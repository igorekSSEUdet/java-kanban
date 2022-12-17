package manager;

import historyManager.HistoryManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;


public class InMemoryTaskManager implements TaskManager {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private Integer id = 0;


    private HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    @Override
    public int addTask(Task task) {
        task.setStatus(Status.NEW);
        task.setId(id++);
        tasks.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public Task getTaskById(int id) {

        if (tasks.containsKey(id)) {
            inMemoryHistoryManager.add(tasks.get(id));
            return tasks.get(id);
        } else {
            return null;
        }
    }


    @Override
    public void updateTask(Task task) {

        if (!tasks.containsKey(task.getId())) {
            return;
        }
        tasks.put(task.getId(), task);
    }

    @Override
    public void removeAllTasks() {
        for (Task task : tasks.values()) {
            inMemoryHistoryManager.remove(task.getId());
        }
        tasks.clear();
    }

    @Override
    public void removeTaskById(int id) {
        inMemoryHistoryManager.remove(id);
        tasks.remove(id);

    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public int addEpic(Epic epic) {
        epic.setId(id++);
        epic.setStatus(Status.NEW);
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            inMemoryHistoryManager.remove(epic.getId());
            for (int i = 0; i < getSubtasksByEpicId(epic.getId()).size(); i++) {
                inMemoryHistoryManager.remove(getSubtasksByEpicId(epic.getId()).get(i).getId());
            }
        }
        epics.clear();
        subtasks.clear();

    }

    @Override
    public void removeEpicById(int id) {
        if (!epics.containsKey(id)) {
            return;
        }
        Epic epic = epics.get(id);
        for (int i = 0; i < epic.lengthList(); i++) {
            subtasks.remove(epic.getIdOfSubtask(i));
            inMemoryHistoryManager.remove(epic.getIdOfSubtask(i));
        }
        epic.removeSubtasks();
        epics.remove(id);
        inMemoryHistoryManager.remove(id);

    }

    @Override
    public Epic getEpicById(int id) {

        if (!epics.containsKey(id)) {
            return null;
        } else {
            inMemoryHistoryManager.add(epics.get(id));
            return epics.get(id);
        }

    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasksByEpicId(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Subtask> listOfSubtasks;
        if (epic == null) {
            return null;
        } else {
            ArrayList<Integer> subtasksIds = epic.getSubtaskIds();
            listOfSubtasks = new ArrayList<>();
            for (Integer subtasksId : subtasksIds) {
                listOfSubtasks.add(subtasks.get(subtasksId));
            }
        }
        return listOfSubtasks;
    }

    @Override
    public int addSubtask(Subtask subtask) {
        subtask.setId(id++);
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.setSubtaskIds(subtask.getId());
        updateStatus(epic.getId());
        return subtask.getId();
    }

    @Override
    public void removeAllSubtasks() {

        for (Subtask subtask : subtasks.values()) {
            inMemoryHistoryManager.remove(subtask.getId());
        }
        for (int i = 0; i < epics.size(); i++) {
            for (Integer integer : epics.keySet()) {
                Epic epic = epics.get(integer);
                epic.removeSubtasks();

                updateStatus(epic.getId());
            }
        }
        subtasks.clear();
    }

    @Override
    public void removeSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (!subtasks.containsKey(id)) {
            return;
        }
        subtasks.remove(id);
        Epic epic = epics.get(subtask.getEpicId());
        epic.removeIdSubtasks(id);
        updateStatus(epic.getId());
        inMemoryHistoryManager.remove(id);

    }

    @Override
    public Subtask getSubtaskById(int id) {
        if (!subtasks.containsKey(id)) {
            return null;
        } else {
            inMemoryHistoryManager.add(subtasks.get(id));
            return subtasks.get(id);
        }

    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (!subtasks.containsKey(subtask.getId())) {
            return;
        }
        subtasks.remove(subtask.getId());
        subtasks.put(subtask.getId(), subtask);

        Epic epic = epics.get(subtask.getEpicId());
        updateStatus(epic.getId());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }


    private Subtask getSubtaskForStatus(int id) {
        return subtasks.getOrDefault(id, null);
    }


    private void updateStatus(int EpicId) {


        int progress = 0;
        int done = 0;
        int NEW = 0;

        Epic epic = epics.get(EpicId);
        ArrayList<Integer> subtaskIds = epic.getSubtaskIds();
        if (subtaskIds.isEmpty()) {
            epic.setStatus(Status.NEW);
        } else {
            for (Integer subtaskId : subtaskIds) {
                Subtask subtask = getSubtaskForStatus(subtaskId);

                if (Status.DONE.equals(subtask.getStatus())) {
                    done++;
                } else if (Status.NEW.equals(subtask.getStatus())) {
                    NEW++;
                } else if (Status.IN_PROGRESS.equals(subtask.getStatus())) {
                    progress++;
                }
            }
            if (done > 0 && progress == 0 && NEW == 0) {
                epic.setStatus(Status.DONE);
            } else if (NEW > 0 && progress == 0 && done == 0) {
                epic.setStatus(Status.NEW);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }
}