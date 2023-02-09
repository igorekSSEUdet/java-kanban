package managers.HttpManager.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import exceptions.TaskManagerException;
import managers.inMemoryManager.TaskManager;
import model.Epic;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class EpicHandler extends TaskHandler implements HttpHandler {

    public EpicHandler(TaskManager manager, Gson gson) {
       super(manager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();


        switch (method) {

            case "GET":
                getEpic(exchange);
                break;
            case "POST":
                addEpic(exchange);
                break;
            case "DELETE":
                removeEpic(exchange);
                break;

        }
    }

    private void getEpic(HttpExchange exchange) throws IOException {

        if (getId(exchange).isEmpty()) {
            writeResponse(exchange, "Некорректный идентификатор поста", 400);
            return;
        }
        if (getId(exchange).get() == -1) {
            getAllEpics(exchange);
            return;
        }
        int epicId = getId(exchange).get();

        try {
            Epic epic = manager.getEpicById(epicId);
            String response = gson.toJson(epic);
            writeResponse(exchange, response, 400);
        } catch (NullPointerException | TaskManagerException ex) {
            writeResponse(exchange, "Нет эпика с таким ID", 400);
        }
    }

    private void getAllEpics(HttpExchange exchange) throws IOException {
        String bytes = gson.toJson(manager.getAllEpics());
        writeResponse(exchange, bytes, 200);

    }

    private void addEpic(HttpExchange exchange) throws IOException {
        InputStream stream = exchange.getRequestBody();

        String epicJson = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        Epic epic = gson.fromJson(epicJson, Epic.class);
        if (epic != null) {
            if (manager.getEpicsMap().containsKey(epic.getId())) {
                manager.updateEpic(epic);
                writeResponse(exchange, "Эпик обновлен", 200);
            } else {
                manager.addEpic(epic);
                writeResponse(exchange, "Эпик добавлен", 200);
            }
        }
        writeResponse(exchange, "Некорректный идентификатор поста", 400);
    }

    private void removeEpic(HttpExchange exchange) throws IOException {
        if (getId(exchange).isEmpty()) {
            writeResponse(exchange, "Некорректный идентификатор эпика", 400);
            return;
        }
        if (getId(exchange).get() == -1) {

            if (manager.getEpicsMap().isEmpty()) {
                writeResponse(exchange, "Эпиков пока нет", 400);
            } else {
                removeAllEpics(exchange);
                return;
            }
        }
        int epicId = getId(exchange).get();
        try {
            if (manager.getEpicsMap().containsKey(epicId)) {
                manager.removeEpicById(epicId);
                writeResponse(exchange, "Эпик удален", 200);
            } else {
                writeResponse(exchange, "Такого эпика нет", 400);
            }
        } catch (NullPointerException | TaskManagerException ex) {
            writeResponse(exchange, "Нет эпика с таким ID", 400);
        }
    }

    private void removeAllEpics(HttpExchange exchange) throws IOException {
        manager.removeAllEpics();
        writeResponse(exchange, "Удалены все эпики", 200);

    }
}
