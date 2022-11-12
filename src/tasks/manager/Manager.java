package tasks.manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.HashMap;
import java.util.ArrayList;


public class Manager implements TopManager{

    HashMap<Integer, Task> tasks = new HashMap<>();//задачи
    HashMap<Integer, Epic> epics = new HashMap<>();//эпики
    HashMap<Integer, Subtask> subtasks = new HashMap<>();//подзадачи
    Integer id = 0;

    public void addTask(Task task) {//добавить задачу
        task.setNumber(id);
        tasks.put(task.getNumber(), task);
    }

    public void removeAllTasks() {//удаление всех задач
        tasks.clear();
    }

    public void removeIdTask(int id) {//удаление по id
        Task task = tasks.remove(id);
    }

    public void getIdTask(int id) {//получить задачу по id|| СДЕЛАТЬ ПРОВЕРКУ НА НОЛЬ!!!!!!!!!!!!!1
        Task task = tasks.get(id);
    }

    public void updateIdTask(Task task) {//обновление задачи
        if (!tasks.containsKey(task.getNumber())) {
            return;
        }
        tasks.put(task.getNumber(), task);
    }

    public ArrayList<Task> getAlltasks() {//получение списка
        return new ArrayList<>(tasks.values());
    }
//--------------------------------------эпики---------------------------------------------------------------------------

    public void addEpic(Epic epic) {//добавили эпик
        epic.setNumber(id++);
        epics.put(epic.getNumber(), epic);
    }

    public void removeAllEpics() {//удалить все эпики
        epics.clear();
        subtasks.clear();
    }

    public void removeIdEpic(int id) {//удалить по id
        if (!epics.containsKey(id)) {
            return;
        }
        epics.remove(id);
    }

    public void getEpic(int id) {//получить эпик по id
        if (!epics.containsKey(id)) {
            return;
        }
        epics.get(id);
    }

    public void updateEpic(Epic epic) {//обновление эпика
        Epic lastepic = epics.get(epic.getNumber());
        if (lastepic == null) {
            return;
        }
        lastepic.setName(epic.getName());
        lastepic.setDescription(epic.getDescription());
    }

    public ArrayList<Epic> getAllEpics() {//получить все эпики
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getSubtasks(int epicId) {//получение подзадач определенного эпика
        return epics.get(epicId).getSubtasks();
    }
    //---------------------------------------подзадачи------------------------------------------------------------------

    public void addSubtask(Subtask subtask) {//добавить
        subtask.setNumber(id);
        subtasks.put(subtask.getNumber(), subtask);
        updateStatusEpic(subtask.getEpicId());
    }

    public void removeAllSubtasks() {//удалить все
        subtasks.clear();
    }

    public void removeIdSubtask(int number) {//удалить
        if (!subtasks.containsKey(number)) {
            return;
        }
        subtasks.remove(number);
        updateStatusEpic(subtasks.get(number).getEpicId());
    }

    public void getIdSubtask(int number) {//получить
        if (!subtasks.containsKey(number)) {
            return;
        }
        subtasks.get(number);
    }

    public void updateSubtask(Subtask subtask) {//обновить
        if (!subtasks.containsKey(subtask.getNumber())) {
            return;
        }
        subtasks.put(subtask.getNumber(), subtask);
        updateStatusEpic(subtask.getEpicId());
    }

    public ArrayList<Subtask> getAllSubtasks() {//получить все
        return new ArrayList<>(subtasks.values());
    }

    private void updateStatusEpic(int epicId) {
        epics.get(epicId).updateStatus();
    }
}