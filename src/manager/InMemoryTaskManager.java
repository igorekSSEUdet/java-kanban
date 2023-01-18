package manager;

import historyManager.HistoryManager;
import model.Epic;
import model.Subtask;
import model.Task;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static model.TaskType.*;


public class InMemoryTaskManager implements TaskManager {

    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();
    protected Map<Integer, Subtask> subtasks = new HashMap<>();
    protected Integer id = 0;


     protected HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    @Override
    public void addTask(Task task) {
        task.setStatus(Status.NEW);
        task.setId(id++);
        task.setTaskType(TASK);
        tasks.put(task.getId(), task);
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
    public void addEpic(Epic epic) {
        epic.setId(id++);
        epic.setStatus(Status.NEW);
        epic.setTaskType(EPIC);
        epics.put(epic.getId(), epic);
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
        ArrayList<Subtask> listOfSubtasks = new ArrayList<>();;
        if (epic == null) {
            return null;
        } else {
            ArrayList<Integer> subtasksIds = epic.getSubtaskIds();
            for (Integer subtasksId : subtasksIds) {
                listOfSubtasks.add(subtasks.get(subtasksId));
            }
        }
        return listOfSubtasks;
    }

    @Override
    public void addSubtask(Subtask subtask) {
        subtask.setId(id++);
        subtask.setStatus(Status.NEW);
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.setSubtaskIds(subtask.getId());
        subtask.setTaskType(SUBTASK);
        updateStatus(epic.getId());
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