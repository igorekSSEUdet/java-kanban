package historyManager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface HistoryManager {
     void add(Task task);

     ArrayList<Task> getHistory();
     void remove(int id);
}
