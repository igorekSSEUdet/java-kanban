package managers.HttpManager.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import exceptions.TaskManagerException;
import managers.taskManager.TaskManager;
import model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class TaskHandler implements HttpHandler {

    private final TaskManager manager;
    private final Gson gson;

    public TaskHandler(TaskManager manager,Gson gson) {
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
            writeResponse(exchange, "Некорректный идентификатор задачи", 400);
            return;
        }
        if (getId(exchange).get() == -1) {
            removeAllTasks(exchange);
            return;
        }
        int taskId = getId(exchange).get();


        try{
            manager.removeTaskById(taskId);
            writeResponse(exchange, "Задача удалена", 200);
        } catch (NullPointerException | TaskManagerException ex) {
            writeResponse(exchange, "Нет задачи с таким ID", 400);
        }
    }

    private void addTask(HttpExchange exchange) throws IOException {
        InputStream stream = exchange.getRequestBody();

        String taskJson = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        Task task = gson.fromJson(taskJson, Task.class);
        if (task != null) {
            if (manager.getTaskMap().containsKey(task.getId())) {
                manager.updateTask(task);
                writeResponse(exchange, "Задача обновлена", 200);
            } else {
                manager.addTask(task);
                writeResponse(exchange, "Задача добавлена", 200);
                return;
            }
        }
        writeResponse(exchange, "Ошибка добавления задачи", 400);

    }

    private void getAllTasks(HttpExchange exchange) throws IOException {

        String allTasksBytes = gson.toJson(manager.getAllTasks());
        writeResponse(exchange, allTasksBytes, 200);

    }

    private void getTask(HttpExchange exchange) throws IOException {

        if (getId(exchange).isEmpty()) {
            writeResponse(exchange, "Некорректный идентификатор поста", 400);
            return;
        }
        if (getId(exchange).get() == -1) {
            if (manager.getTaskMap().isEmpty()) {
                writeResponse(exchange, "Задач пока нет", 400);
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
            writeResponse(exchange, "нет задачи с таким ID", 200);
        }
    }


    private void removeAllTasks(HttpExchange exchange) throws IOException {
        manager.removeAllTasks();
        writeResponse(exchange, "Все задачи удалены", 200);



    }

    private void writeResponse(HttpExchange exchange, String response, int responseCode) throws IOException {

        if (response.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] dataBytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(responseCode, dataBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(dataBytes);
            }
        }
        exchange.close();
    }

    private Optional<Integer> getId(HttpExchange exchange) {
        int x = exchange.getRequestURI().getPath().split("/").length;
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
