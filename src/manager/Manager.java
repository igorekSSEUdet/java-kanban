package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.HashMap;
import java.util.ArrayList;


public class Manager {

    private static HashMap<Integer, Task> tasks = new HashMap<>();
    private static HashMap<Integer, Epic> epics = new HashMap<>();
    private static HashMap<Integer, Subtask> subtasks = new HashMap<>();
    Integer id = 0;

    public void addTask(Task task) { // добавить задачу(C)
        task.setId(id++);
        tasks.put(task.getId(), task);
    }

    public Object getIdTask(int id) { // получить задачу по id(R)

        if (tasks.containsKey(id)) {
            Task task = tasks.get(id);
            return task;
        } else {
            return null;
        }
    }

    public void updateIdTask(Task task) { // обновление задачи(U)
        if (!tasks.containsKey(task.getId())) {
            return;
        }
        tasks.put(task.getId(), task);
    }

    public void removeAllTasks() { // удаление всех задач(D)
        tasks.clear();
    }

    public void removeIdTask(int id) { // удаление по id(D)
        Task task = tasks.remove(id);
    }


    public ArrayList<Task> getAlltasks() { // получение списка
        return new ArrayList<>(tasks.values());
    }


    public int addEpic(Epic epic) { // добавили эпик
        epic.setId(id++);
        epic.setStatus("NEW");
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    public void removeAllEpics() { // удалить все эпики
        epics.clear();
        subtasks.clear();
    }

    public void removeIdEpic(int id) { // удалить по id
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

    public Epic getEpic(int id) { // получить эпик по id
        if (!epics.containsKey(id)) {
            return null;
        }
        return epics.get(id);
    }

    public void updateEpic(Epic epic) { // обновление эпика
        epics.put(epic.getId(), epic);
    }

    public ArrayList<Epic> getAllEpics() { // получить все эпики
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getSubtasks(int epicId) { // получение подзадач определенного эпика
        Epic epic = epics.get(epicId);
        ArrayList<Integer> subtasksIds = epic.getSubtaskIds();
        ArrayList<Subtask> listOfSubtasks = new ArrayList<>();
        for (Integer subtasksId : subtasksIds) {
            listOfSubtasks.add(subtasks.get(subtasksId));
        }
        return listOfSubtasks;
    }


    public void addSubtask(Subtask subtask) { // добавить
        subtask.setId(id++);
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.setSubtaskIds(subtask.getId());
        epic.getUpdateStatus();
    }

    public void removeAllSubtasks() { // удалить все
        subtasks.clear();
        for (int i = 0; i < epics.size(); i++) {
            for (Integer integer : epics.keySet()) {
                Epic epic = epics.get(integer);
                epic.removeSubtasks();
                epic.getUpdateStatus();
            }
        }
    }

    public void removeIdSubtask(int number) { // удалить
        Subtask subtask = subtasks.get(number);
        if (!subtasks.containsKey(number)) {
            return;
        }
        subtasks.remove(number);
        Epic epic = epics.get(subtask.getEpicId());
        epic.removeIdSubtasks(number);
        epic.getUpdateStatus();
    }

    public void getIdSubtask(int number) { // получить
        if (!subtasks.containsKey(number)) {
            return;
        }
        subtasks.get(number);
    }

    public void updateSubtask(Subtask subtask) { // обновить
        if (!subtasks.containsKey(subtask.getId())) {
            return;
        }
        subtasks.remove(subtask.getId());
        subtasks.put(subtask.getId(), subtask);

        Epic epic = epics.get(subtask.getEpicId());
        epic.getUpdateStatus();
    }

    public ArrayList<Subtask> getAllSubtasks() { // получить все подзадачи определенного эпика
        return new ArrayList<>(subtasks.values());
    }

    public Subtask getIdSubtaskForStatus(int number) { // получить id подзадач определенного эпика
        return subtasks.get(number);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }


}