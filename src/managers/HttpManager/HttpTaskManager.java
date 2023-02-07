package managers.HttpManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import managers.fileBackedManager.FileBackedTasksManager;
import model.Epic;
import model.Subtask;
import model.Task;

import java.io.IOException;


public class HttpTaskManager extends FileBackedTasksManager {


    Gson gson = new Gson();
    KVTaskClient kvClient;

    public HttpTaskManager(String url) throws IOException, InterruptedException {
        this.kvClient = new KVTaskClient(url);
    }

    public void save() {
        kvClient.put("tasks", gson.toJson(getAllTasks()));
        kvClient.put("subTasks", gson.toJson(getAllSubTasks()));
        kvClient.put("epics", gson.toJson(getAllEpics()));
        kvClient.put("history", gson.toJson(getHistory()));
    }


    public static HttpTaskManager loadFromServer() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpTaskManager manager = new HttpTaskManager("http://localhost:8078");
        int maxId = 1;

        JsonArray tasks = manager.kvClient.load("tasks");

        if (!(tasks == null)) {
            for (JsonElement task : tasks) {
                Task task1 = gson.fromJson(task, Task.class);
                manager.tasks.put(task1.getId(), task1);
                manager.sortedTasks.add(task1);
                if (task1.getId() > maxId) maxId = task1.getId();
            }

        }


        JsonArray epics = manager.kvClient.load("epics");
        if (!(epics == null)) {
            for (JsonElement task : epics) {
                Epic epic = gson.fromJson(task, Epic.class);
                manager.epics.put(epic.getId(), epic);
                if (epic.getId() > maxId) maxId = epic.getId();

            }
        }
        JsonArray subTasks = manager.kvClient.load("subTasks");
        if (!(subTasks == null)) {
            for (JsonElement task : subTasks) {
                Subtask subtask = gson.fromJson(task, Subtask.class);
                manager.subTasks.put(subtask.getId(),subtask);
                manager.sortedTasks.add(subtask);
                if (subtask.getId() > maxId) maxId = subtask.getId();

            }
        }

        JsonArray historyArr = manager.kvClient.load("history");
        if (!historyArr.isEmpty()) {
            for (JsonElement history : historyArr) {
                manager.historyManager.add(gson.fromJson(history, Task.class));
            }
        }
        manager.id = maxId;
        return manager;
    }


}
