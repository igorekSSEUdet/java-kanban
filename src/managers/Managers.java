package managers;

import managers.historyManager.InMemoryHistoryManager;

public class Managers {

//    public static TaskManager getDefault() {
//        return new FileBackedTasksManager();
//    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}