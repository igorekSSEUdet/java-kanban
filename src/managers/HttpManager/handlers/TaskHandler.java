package managers.HttpManager.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import exceptions.TaskManagerException;
import managers.inMemoryManager.TaskManager;
import model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static managers.HttpManager.ResponseCodes.*;

public class TaskHandler extends UtilHandler implements HttpHandler {

    protected TaskManager manager;
    protected Gson gson;

    public TaskHandler(TaskManager manager, Gson gson) {
        this.manager = manager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();


        switch (method) {

            case "GET":
                getTask(exchange);
                break;
            case "POST":
                addTask(exchange);
                break;
            case "DELETE":
                removeTask(exchange);
                break;

        }

    }

    private void removeTask(HttpExchange exchange) throws IOException {

        if (getId(exchange).isEmpty()) {
            writeResponse(exchange, "Некорректный идентификатор задачи", BAD_REQUEST.getCode());
            return;
        }
        if (getId(exchange).get() == -1) {
            removeAllTasks(exchange);
            return;
        }
        int taskId = getId(exchange).get();


        try {
            manager.removeTaskById(taskId);
            writeResponse(exchange, "Задача удалена", OK.getCode());
        } catch (NullPointerException | TaskManagerException ex) {
            writeResponse(exchange, "Нет задачи с таким ID", NOT_FOUND.getCode());
        }
    }

    private void addTask(HttpExchange exchange) throws IOException {
        InputStream stream = exchange.getRequestBody();

        String taskJson = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        Task task = gson.fromJson(taskJson, Task.class);
        if (task != null) {
            if (manager.getTaskMap().containsKey(task.getId())) {
                manager.updateTask(task);
                writeResponse(exchange, "Задача обновлена", CREATED.getCode());
            } else {
                manager.addTask(task);
                writeResponse(exchange, "Задача добавлена", CREATED.getCode());
                return;
            }
        }
        writeResponse(exchange, "Ошибка добавления задачи", BAD_REQUEST.getCode());

    }

    private void getAllTasks(HttpExchange exchange) throws IOException {

        String allTasksBytes = gson.toJson(manager.getAllTasks());
        writeResponse(exchange, allTasksBytes, OK.getCode());

    }

    private void getTask(HttpExchange exchange) throws IOException {

        if (getId(exchange).isEmpty()) {
            writeResponse(exchange, "Некорректный идентификатор поста", BAD_REQUEST.getCode());
            return;
        }
        if (getId(exchange).get() == -1) {
            if (manager.getTaskMap().isEmpty()) {
                writeResponse(exchange, "Задач пока нет", NO_CONTENT.getCode());
            } else {
                getAllTasks(exchange);
                return;
            }
        }
        int taskId = getId(exchange).get();

        try {
            Task task = manager.getTaskById(taskId);
            String response = gson.toJson(task);
            writeResponse(exchange, response, 200);
        } catch (NullPointerException ex) {
            writeResponse(exchange, "нет задачи с таким ID", NOT_FOUND.getCode());
        }
    }


    private void removeAllTasks(HttpExchange exchange) throws IOException {
        manager.removeAllTasks();
        writeResponse(exchange, "Все задачи удалены", OK.getCode());


    }

    protected Optional<Integer> getId(HttpExchange exchange) {
        if (exchange.getRequestURI().getQuery() == null) {
            return Optional.of(-1);
        }
        String[] pathParts = exchange.getRequestURI().getQuery().split("=");

        try {
            return Optional.of(Integer.parseInt(pathParts[1]));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }

    }
}
