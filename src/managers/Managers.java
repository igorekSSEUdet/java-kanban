package managers;

import managers.HttpManager.HttpTaskManager;
import managers.fileBackedManager.FileBackedTasksManager;
import managers.historyManager.InMemoryHistoryManager;
import managers.taskManager.TaskManager;

import java.io.IOException;

public class Managers {

    public static TaskManager getDefault() {
     return new FileBackedTasksManager();
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}