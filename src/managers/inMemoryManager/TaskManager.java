package managers.inMemoryManager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TaskManager {

    void addTask(Task task);

    void updateTask(Task task);

    void removeSubtasksInEpic(int id);

    void removeTaskById(int id);

    Task getTaskById(int id);

    List<Task> getAllTasks();

    void removeAllTasks();

    void addSubTask(Subtask subtask);

    void updateSubTask(Subtask task);

    void removeSubTaskById(int id);

    Subtask getSubTaskById(int id);

    List<Subtask> getAllSubTasks();

    void removeAllSubTasks();

    void addEpic(Epic subtask);

    void updateEpic(Epic epic);

    void removeEpicById(int id);

    Epic getEpicById(int id);

    List<Epic> getAllEpics();

    void removeAllEpics();

    List<Subtask> getSubtasksByEpic(int id);

    List<Task> getHistory();

    Set<Task> getPrioritizedTasks();

    Map<Integer, Task> getTaskMap();

    Map<Integer, Subtask> getSubTasksMap();

    Map<Integer, Epic> getEpicsMap();

}
