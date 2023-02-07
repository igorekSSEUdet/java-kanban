package managers.HttpManager.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import exceptions.TaskManagerException;
import managers.taskManager.TaskManager;
import model.Subtask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class SubtaskHandler implements HttpHandler {
    private final TaskManager manager;
    private final Gson gson;

    public SubtaskHandler(TaskManager manager,Gson gson) {
        this.manager = manager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();


        switch (method) {

            case "GET":
                getSubtaskById(exchange);
                break;
            case "POST":
                addSubtaskById(exchange);
                break;
            case "DELETE":
                removeSubtaskById(exchange);
                break;

        }
    }

    private void getSubtaskById(HttpExchange exchange) throws IOException {

        if (getId(exchange).isEmpty()) {
            writeResponse(exchange, "Некорректный идентификатор поста", 400);
            return;
        }
        if (getId(exchange).get() == -1) {
            if (manager.getSubTasksMap().isEmpty()) {
                writeResponse(exchange, "Подзадач пока нет", 200);
            } else {
                getAllSubtask(exchange);
                writeResponse(exchange, "Все подзадачи", 200);
            }
            return;

        }
        int subtaskId = getId(exchange).get();


        try {
            Subtask subtask = manager.getSubTaskById(subtaskId);
            String response = gson.toJson(subtask);
            writeResponse(exchange, response, 200);
        } catch (NullPointerException | TaskManagerException ex) {
            writeResponse(exchange, "Нет подзадачи с таким ID", 400);
        }


    }

    private void getAllSubtask(HttpExchange exchange) throws IOException {
        String allSubtasks = gson.toJson(manager.getAllSubTasks());
        writeResponse(exchange, allSubtasks, 200);

    }

    private void addSubtaskById(HttpExchange exchange) throws IOException {
        InputStream stream = exchange.getRequestBody();
        String subtaskJson = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        Subtask subtask = gson.fromJson(subtaskJson, Subtask.class);
        if (subtask.getEpicId() == null || (!manager.getEpicsMap().containsKey(subtask.getEpicId()))  ) {
            writeResponse(exchange, "Подзадача не может существовать без эпика", 200);
        }
        if (subtask != null) {

            if (manager.getSubTasksMap().containsKey(subtask.getId())) {
                manager.updateSubTask(subtask);
                writeResponse(exchange, "Подзадача обновлена", 200);
            } else {
                manager.addSubTask(subtask);
                writeResponse(exchange, "Подзадача добавлена", 200);
            }
        }
        writeResponse(exchange, "Некорректный идентификатор поста", 400);

    }

    private void removeSubtaskById(HttpExchange exchange) throws IOException {
        if (getId(exchange).isEmpty()) {
            writeResponse(exchange, "Некорректный идентификатор эпика", 400);
            return;
        }
        if (getId(exchange).get() == -1) {
            removeAllSubtask(exchange);
            return;
        }
        int subtaskId = getId(exchange).get();
        try {
            manager.removeSubTaskById(subtaskId);
            writeResponse(exchange, "подзадача удалена", 200);
        } catch (NullPointerException | TaskManagerException ex) {
            writeResponse(exchange, "Нет задачи с таким ID", 400);
        }


    }

    private void removeAllSubtask(HttpExchange exchange) throws IOException {
        manager.removeAllSubTasks();
        writeResponse(exchange, "Все подзадачи удалены", 400);

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

        if (exchange.getRequestURI().getQuery()==null) {
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
