package managers.taskManager;

import exceptions.TaskManagerException;
import managers.Managers;
import managers.historyManager.InMemoryHistoryManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.*;

import static model.Status.NEW;
import static model.TaskType.*;

public class InMemoryTaskManager implements TaskManager {

    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, Subtask> subTasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();
    protected Set<Task> sortedTasks = new TreeSet<>((first, second) -> {
        if (first.getStartTime() == null) return 1;
        else if (first.getStartTime() != null && second.getStartTime() == null) return -1;
        else if (first.getStartTime() == null && second.getStartTime() == null) return 0;
        else if (first.getStartTime().isAfter(second.getEndTime())) return 1;
        else if (first.getStartTime().isBefore(second.getEndTime())) return -1;

        else return 0;
    });
    protected Integer id = 0;

    protected InMemoryHistoryManager historyManager = Managers.getDefaultHistory();

    //INTERSECTION
    protected boolean checkIfIntersection(Task task) {
        boolean check = true;
        if (sortedTasks.isEmpty()) check = true;
        for (Task sortedTask : sortedTasks) {

            if (task.getStartTime() == null && sortedTask.getStartTime() == null) {
                check = true;
            } else if (task.getStartTime() == null && sortedTask.getStartTime() != null) {
                check = true;
            } else if (task.getStartTime() != null && sortedTask.getStartTime() == null) {
                check = true;
            } else if (sortedTask.getStartTime() == task.getStartTime()) {
                check = false;
            } else if (task.getStartTime().isAfter(sortedTask.getStartTime()) &&
                    task.getEndTime().isBefore(sortedTask.getEndTime())) {
                check = false;
            }
        }
        return check;
    }


    public Set<Task> getPrioritizedTasks() {
        return this.sortedTasks;
    }

    protected void removeTasksInSorted() {
        sortedTasks.removeIf(taskTime -> taskTime.getTaskType() == TASK);
    }

    protected void removeSubTasksInSorted() {
        sortedTasks.removeIf(t -> t.getTaskType() == SUBTASK);
    }


    //TASKS
    @Override
    public void addTask(Task task) {

        if (checkIfIntersection(task)) {
            try {
                task.setId(id++);
                task.setTaskType(TASK);
                tasks.put(task.getId(), task);
                sortedTasks.add(task);
            } catch (TaskManagerException ex) {
                throw new TaskManagerException("Ошибка добавления задачи");
            }

        } else {
            throw new TaskManagerException("Задачи не должны пересекаться по времени");
        }
    }

    @Override
    public void updateTask(Task task) {
        if (checkIfIntersection(task)) {
            try {
                if (!tasks.containsKey(task.getId())) throw new NullPointerException("Такой задачи нет");
                tasks.put(task.getId(), task);
            } catch (TaskManagerException ex) {
                throw new TaskManagerException("Ошибка обновления задачи");
            }
        } else {
            throw new TaskManagerException("Задачи не должны пересекаться по времени");
        }
    }

    @Override
    public void removeTaskById(int id) {

        try {
            if (!tasks.containsKey(id)) throw new NullPointerException("Такой задачи нет");
            historyManager.remove(id);
            tasks.remove(id);
        } catch (TaskManagerException ex) {
            throw new TaskManagerException("Ошибка удаления задачи");
        }

    }

    @Override
    public Task getTaskById(int id) {
        if (!tasks.containsKey(id)) throw new NullPointerException("Такой задачи нет");
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());

    }

    @Override
    public void removeAllTasks() {
        removeTasksInSorted();
        tasks.clear();
    }


    //SUBTASKS
    @Override
    public void addSubTask(Subtask subtask) {
        if (checkIfIntersection(subtask)) {
            try {
                subtask.setId(id++);
                subtask.setTaskType(SUBTASK);
                subTasks.put(subtask.getId(), subtask);
                sortedTasks.add(subtask);
                Epic epic = epics.get(subtask.getEpicId());
                epic.plusDuration(subtask);
                epic.calculateEpicTime(subtask);
                epic.addEpicsSubtask(subtask);
                updateStatus(epic);
            } catch (TaskManagerException | NullPointerException exception) {
                throw new TaskManagerException("Ошибка добавления подзадачи");
            }

        } else {
            throw new TaskManagerException("Задачи не должны пересекаться по времени");
        }
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        if (checkIfIntersection(subtask)) {
            try {
                if (!subTasks.containsKey(subtask.getId())) throw new NullPointerException("Такой подзадачи нет");
                ;
                Epic epic = epics.get(subtask.getEpicId());
                epic.removeEpicsSubtask(subTasks.get(subtask.getId()));
                sortedTasks.remove(subTasks.get(subtask.getId()));
                subTasks.put(subtask.getId(), subtask);
                sortedTasks.add(subtask);
                epic.addEpicsSubtask(subtask);
                updateStatus(epic);
            } catch (TaskManagerException ex) {
                throw new TaskManagerException("Ошибка обновления подзадачи");
            }
        } else {
            throw new TaskManagerException("Подзадачи не должны пересекаться по времени");
        }
    }

    @Override
    public void removeSubTaskById(int id) {
        try {
            if (!subTasks.containsKey(id)) throw new NullPointerException("Такой подзадачи нет");
            Epic epic = epics.get(subTasks.get(id).getEpicId());
            epic.minusDuration(subTasks.get(id));
            epic.calculateEpicTime(subTasks.get(id));
            epic.removeEpicsSubtask(subTasks.get(id));
            updateStatus(epic);
            historyManager.remove(id);
            subTasks.remove(id);
        } catch (TaskManagerException ex) {
            throw new TaskManagerException("Ошибка удаления подзадачи");
        }
    }

    @Override
    public Subtask getSubTaskById(int id) {
        if (!subTasks.containsKey(id)) throw new NullPointerException("Такой подзадачи нет");
        historyManager.add(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public List<Subtask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void removeAllSubTasks() {
        removeSubTasksInSorted();
        removeAllSubtasksInEpic();
        subTasks.clear();
    }

    @Override
    public void addEpic(Epic epic) {
        try {
            epic.setId(id++);
            epic.setTaskType(EPIC);
            epic.setStatus(NEW);
            epics.put(epic.getId(), epic);
        } catch (TaskManagerException ex) {
            throw new TaskManagerException("Ошибка добавления подзадачи");
        }

    }


    @Override
    public void updateEpic(Epic epic) {

        try {
            if (!epics.containsKey(epic.getId())) throw new NullPointerException("Такого эпика нет");
            epics.put(epic.getId(), epic);
        } catch (TaskManagerException ex) {
            throw new TaskManagerException("Ошибка обновления эпика");
        }

    }

    @Override
    public void removeEpicById(int id) {
        try {
            if (!epics.containsKey(id)) return;
            historyManager.remove(id);
            removeSubtaskBecauseEpic(id);
            epics.remove(id);
        } catch (TaskManagerException ex) {
            throw new TaskManagerException("Ошибка удаления эпика");
        }
    }

    @Override
    public Epic getEpicById(int id) {
        if (!epics.containsKey(id)) throw new NullPointerException("Такого эпика нет");
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void removeAllEpics() {
        removeAllSubTasks();
        epics.clear();
    }

    @Override
    public List<Subtask> getSubtasksByEpic(int id) {
        if (!epics.containsKey(id)) throw new TaskManagerException("Нет такого эпика");
        Epic epic = epics.get(id);
        return epic.getSubtasks();
    }


    //HISTORY
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    //HELPERS
    private void updateStatus(Epic epic) {
        int progress = 0;
        int done = 0;
        int NEW = 0;

        List<Subtask> subtasksInEpic = epic.getSubtasks();
        if (subtasksInEpic.isEmpty()) {
            epic.setStatus(Status.NEW);
        } else {
            for (Subtask subtask : subtasksInEpic) {

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


    public Map<Integer,Task>getTaskMap() {
        return tasks;
    }
    public Map<Integer,Subtask>getSubTasksMap() {
        return subTasks;
    }
    public Map<Integer,Epic>getEpicsMap() {
        return epics;
    }

    protected void removeAllSubtasksInEpic() {
        if (epics.isEmpty()) return;
        for (Epic epic : epics.values()) {
            epic.clearSubtasksList();
            updateStatus(epic);
        }
    }

    protected void removeSubtaskBecauseEpic(int id) {
        Epic epic = epics.get(id);
        if (epic.getSubtasks().isEmpty()) return;
        for (Subtask subtask : epic.getSubtasks()) {
            subTasks.remove(subtask.getId());
        }
    }

    @Override
    public void removeSubtasksInEpic(int id) {
        Epic epic = epics.get(id);
        if (epic.getSubtasks().isEmpty()) return;
        for (Subtask subtask : epic.getSubtasks()) {
            subTasks.remove(subtask.getId());
        }
        epic.clearSubtasksList();
    }


}
