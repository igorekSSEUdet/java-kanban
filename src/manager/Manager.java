package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.HashMap;
import java.util.ArrayList;


public class Manager {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    Integer id = 0;

    public int addTask(Task task) {
        task.setStatus("NEW");
        task.setId(id++);
        tasks.put(task.getId(), task);
        return task.getId();
    }

    public Task getTaskById(int id) {


        if (tasks.containsKey(id)) {
            Task task = tasks.get(id);
            return task;
        } else {
            return null;
        }
    }

    public void updateTask(Task task) {

        if (!tasks.containsKey(task.getId())) {
            return;
        }
        tasks.put(task.getId(), task);
    }

    public void removeAllTasks() {

        tasks.clear();
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }


    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }


    public int addEpic(Epic epic) {
        epic.setId(id++);
        epic.setStatus("NEW");
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    public void removeAllEpics() {

        epics.clear();
        subtasks.clear();
    }

    public void removeEpicById(int id) {
        if (!epics.containsKey(id)) {
            return;
        }
        Epic epic = epics.get(id);
        for (int i = 0; i < epic.lengthList(); i++) {
            subtasks.remove(epic.getIdOfSubtask(i));
        }
        epic.removeSubtasks();
        epics.remove(id);

    }

    public Epic getEpicById(int id) {

        if (!epics.containsKey(id)) {
            return null;
        }
        return epics.get(id);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

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


    public int addSubtask(Subtask subtask) {
        subtask.setId(id++);
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.setSubtaskIds(subtask.getId());
        updateStatus(epic.getId());
        return subtask.getId();
    }

    public void removeAllSubtasks() {
        subtasks.clear();
        for (int i = 0; i < epics.size(); i++) {
            for (Integer integer : epics.keySet()) {
                Epic epic = epics.get(integer);
                epic.removeSubtasks();
                updateStatus(epic.getId());
            }
        }
    }

    public void removeSubtaskById(int number) {
        Subtask subtask = subtasks.get(number);
        if (!subtasks.containsKey(number)) {
            return;
        }
        subtasks.remove(number);
        Epic epic = epics.get(subtask.getEpicId());
        epic.removeIdSubtasks(number);
        updateStatus(epic.getId());
    }

    public Subtask getSubtaskById(int number) {
        if (!subtasks.containsKey(number)) {
            return null;
        }
        return subtasks.get(number);
    }

    public void updateSubtask(Subtask subtask) {
        if (!subtasks.containsKey(subtask.getId())) {
            return;
        }
        subtasks.remove(subtask.getId());
        subtasks.put(subtask.getId(), subtask);

        Epic epic = epics.get(subtask.getEpicId());
        updateStatus(epic.getId());
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }


    public void updateStatus(int EpicId) {
        Integer progress = 0;
        Integer done = 0;
        Integer NEW = 0;

        Epic epic = epics.get(EpicId);
        ArrayList<Integer> subtaskIds = epic.getSubtaskIds();
        if (subtaskIds.isEmpty()) {
            epic.setStatus("NEW");
        } else {
            for (Integer subtaskId : subtaskIds) {
                Subtask subtask = getSubtaskById(subtaskId);

                if ("DONE".equals(subtask.getStatus())) {
                    done++;
                } else if ("NEW".equals(subtask.getStatus())) {
                    NEW++;
                } else if ("IN_PROGRESS".equals(subtask.getStatus())) {
                    progress++;
                }
            }
            if (done > 0 && progress == 0 && NEW == 0) {
                epic.setStatus("DONE");
            } else if (NEW > 0 && progress == 0 && done == 0) {
                epic.setStatus("NEW");
            } else {
                epic.setStatus("IN_PROGRESS");
            }
        }
    }


}