package managers.HttpManager.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.inMemoryManager.TaskManager;
import model.Epic;

import java.io.IOException;
import java.util.Optional;

import static managers.HttpManager.ResponseCodes.*;

public class EpicSubtaskHandler extends UtilHandler implements HttpHandler {

    private final TaskManager manager;
    private final Gson gson;

    public EpicSubtaskHandler(TaskManager manager, Gson gson) {
        this.manager = manager;
        this.gson = gson;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();


        switch (method) {

            case "GET":
                getEpicsSubtasks(exchange);
                break;
            case "DELETE":
                removeAllSubtasksInEpic(exchange);
                break;
        }

    }


    private void getEpicsSubtasks(HttpExchange exchange) throws IOException {
        if (getId(exchange).isEmpty()) {
            writeResponse(exchange, "Некорректный идентификатор поста", BAD_REQUEST.getCode());
            return;
        }
        int epicId = getId(exchange).get();

        for (Epic epic : manager.getAllEpics()) {
            if (epic.getId() == epicId) {
                String response = gson.toJson(epic.getSubtasks());
                writeResponse(exchange, response, OK.getCode());
                return;
            }
        }
        writeResponse(exchange, "Нет эпика с таким ID", NOT_FOUND.getCode());

    }

    private void removeAllSubtasksInEpic(HttpExchange exchange) throws IOException {
        if (getId(exchange).isEmpty()) {
            writeResponse(exchange, "Некорректный идентификатор эпика", BAD_REQUEST.getCode());
            return;
        }
        int epicId = getId(exchange).get();
        for (Epic epic : manager.getAllEpics()) {
            if (epic.getId() == epicId) {
                manager.removeSubtasksInEpic(epicId);
                writeResponse(exchange, "удалены все подзадачи эпика с ID = " + epic.getId(), OK.getCode());
                return;
            }
        }
        writeResponse(exchange, "Нет задачи с таким ID",  NOT_FOUND.getCode());
    }

    private Optional<Integer> getId(HttpExchange exchange) {

        String[] pathParts = exchange.getRequestURI().getQuery().split("=");

        try {
            return Optional.of(Integer.parseInt(pathParts[1]));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }

    }
}
