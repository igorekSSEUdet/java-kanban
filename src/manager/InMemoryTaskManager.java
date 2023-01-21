package manager;

import historyManager.HistoryManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.*;

import static model.TaskType.*;


public class InMemoryTaskManager implements TaskManager {

    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();
    protected Map<Integer, Subtask> subtasks = new HashMap<>();
    protected Integer id = 0;


    protected HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();




    private Collection<Task> getPrioritizedTasks() {
        Set<Task> sortedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
        sortedTasks.addAll(tasks.values());
        sortedTasks.addAll(subtasks.values());
        return sortedTasks;
    }

    private boolean checkIntersection(Task task) {
        int check = 0;
        for (Task priority : getPrioritizedTasks()) {
            if (task.getStartTime().isAfter(priority.getStartTime()) && task.getEndTime().isBefore(priority.getEndTime()))
                check++;
        }
        if (check != 0) return false;
        else return true;
    }

    @Override
    public void addTask(Task task) {
        if (checkIntersection(task)) {
            task.setStatus(Status.NEW);
            task.setId(id++);
            task.setTaskType(TASK);
            tasks.put(task.getId(), task);
        } else System.out.println("Невозможно добавить пересекающиеся по времени задачи");

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
        if (checkIntersection(task)) {
            if (!tasks.containsKey(task.getId())) {
                return;
            }
            tasks.put(task.getId(), task);
        } else System.out.println("Невозможно добавить пересекающиеся по времени задачи");
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
        if (checkIntersection(epic)) {
            epic.setId(id++);
            epic.setStatus(Status.NEW);
            epic.setTaskType(EPIC);
            epics.put(epic.getId(), epic);
        } else System.out.println("Невозможно добавить пересекающиеся по времени задачи");


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
        List<Subtask> subtasks1 = epic.getSubtasks();
        for (int i = 0; i < epic.lengthList(); i++) {
            for (Subtask subtask : subtasks1) {
                subtasks.remove(subtask.getId());
                inMemoryHistoryManager.remove(subtask.getId());
            }
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
        if (checkIntersection(epic)) {
            epics.put(epic.getId(), epic);
        } else System.out.println("Невозможно добавить пересекающиеся по времени задачи");
    }


    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasksByEpicId(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }

        ArrayList<Subtask> listOfSubtasks;
        listOfSubtasks = epic.getSubtasks();
        return listOfSubtasks;
    }

    @Override
    public void addSubtask(Subtask subtask) {
        if (checkIntersection(subtask)) {
            subtask.setId(id++);
            subtask.setStatus(Status.NEW);
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            epic.setSubtask(subtask);
            epic.countTime(subtask);
            epic.plusDuration(subtask.getDuration());
            subtask.setTaskType(SUBTASK);
            updateStatus(epic.getId());
        } else System.out.println("Невозможно добавить пересекающиеся по времени задачи");
    }

    @Override
    public void removeAllSubtasks() {

        for (Subtask subtask : subtasks.values()) {
            inMemoryHistoryManager.remove(subtask.getId());
        }
        for (int i = 0; i < epics.size(); i++) {//check this wtf cycle
            for (Integer integer : epics.keySet()) {
                Epic epic = epics.get(integer);
                epic.emptyDuration();
                epic.removeSubtasks();
                epic.emptyTime();
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
        epic.removeSubtask(subtask);
        epic.minusDuration(subtask.getDuration());
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

        if (checkIntersection(subtask)) {
            if (!subtasks.containsKey(subtask.getId())) {
                return;
            }
           // subtasks.remove(subtask.getId());
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            updateStatus(epic.getId());
        } else System.out.println("Невозможно добавить пересекающиеся по времени задачи");
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
        ArrayList<Integer> subtaskIds = epic.getSubtasksIds();
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