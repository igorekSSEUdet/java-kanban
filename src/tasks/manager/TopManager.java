package tasks.manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

public interface TopManager {

    void addTask(Task task);

    void removeAllTasks();

    void removeIdTask(int id);

    void getIdTask(int id);

    void updateIdTask(Task task);

    ArrayList<Task> getAlltasks();

    void addEpic(Epic epic);

    void removeAllEpics();

    void removeIdEpic(int id);

    void getEpic(int id);

    void updateEpic(Epic epic);

    ArrayList<Epic> getAllEpics();

    ArrayList<Subtask> getSubtasks(int epicId);

    void addSubtask(Subtask subtask);

    void removeAllSubtasks();

    void removeIdSubtask(int number);

    void getIdSubtask(int number);

    void updateSubtask(Subtask subtask);

    ArrayList<Subtask> getAllSubtasks();



}
