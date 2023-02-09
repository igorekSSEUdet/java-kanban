package managers;

import managers.fileBackedManager.FileBackedTasksManager;
import managers.historyManager.InMemoryHistoryManager;
import managers.inMemoryManager.TaskManager;

public class Managers {

    public static TaskManager getDefault() {
     return new FileBackedTasksManager();
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}