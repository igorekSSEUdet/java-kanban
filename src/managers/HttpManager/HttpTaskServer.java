package managers.HttpManager;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import managers.HttpManager.handlers.*;
import managers.Managers;
import managers.taskManager.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;

import java.io.IOException;
import java.net.InetSocketAddress;

import static model.Status.NEW;

public class HttpTaskServer {

    public static void main(String[] args) throws IOException {
        new KVServer().start();
        HttpTaskServer server1 = new HttpTaskServer();
        server1.start();
        Task task = new Task("name", "desc", NEW);
        Epic epic = new Epic("nameEpic", "subEpic");
        server1.manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        server1.manager.addSubTask(subtask);
        server1.manager.addTask(task);
        System.out.println("Сервер запущен");

    }

    private static final int PORT = 8080;
    HttpServer server;
    public TaskManager manager;
    Gson gson = new Gson();

    public HttpTaskServer() throws IOException {
        this.manager = Managers.getDefault();
        this.server = HttpServer.create();
        server.bind(new InetSocketAddress(PORT), 0);
        server.createContext("/tasks/task", new TaskHandler(manager, gson));
        server.createContext("/tasks/epic", new EpicHandler(manager, gson));
        server.createContext("/tasks/subtask/epic", new EpicSubtaskHandler(manager, gson));
        server.createContext("/tasks/history", new HistoryHandler(manager, gson));
        server.createContext("/tasks", new PrioritizedTasks(manager, gson));
        server.createContext("/tasks", new SubtaskHandler(manager, gson));
        server.createContext("/tasks/subtask", new SubtaskHandler(manager, gson));


    }

    public void start() {
        this.server.start();
    }

    public void stop() {
        this.server.stop(0);
    }


}
