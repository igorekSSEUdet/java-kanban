package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    public int addTask(Task task);

    public Task getTaskById(int id);

    public void updateTask(Task task);

    public void removeAllTasks();

    public void removeTaskById(int id);


    public ArrayList<Task> getAllTasks();

    public int addEpic(Epic epic);

    public void removeAllEpics();

    public void removeEpicById(int id);

    public Epic getEpicById(int id);

    public void updateEpic(Epic epic);

    public ArrayList<Epic> getAllEpics();

    public ArrayList<Subtask> getSubtasksByEpicId(int epicId);


    public int addSubtask(Subtask subtask);

    public void removeAllSubtasks();

    public void removeSubtaskById(int number);

    public Subtask getSubtaskById(int number);


    public void updateSubtask(Subtask subtask);

    public ArrayList<Subtask> getAllSubtasks();


    public List<Task> getHistory();

    public void updateStatus(int EpicId);

    public Subtask getSubtaskForStatus(int id);
}
