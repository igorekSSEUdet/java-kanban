package manager;

import historyManager.HistoryManager;
import historyManager.InMemoryHistoryManager;

public class Managers {

    public static TaskManager getDefault() {
        return new FileBackedTasksManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
