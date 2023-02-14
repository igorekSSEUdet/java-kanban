package managers.HttpManager.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.inMemoryManager.TaskManager;

import java.io.IOException;

import static managers.HttpManager.ResponseCodes.METHOD_NOT_ALLOWED;
import static managers.HttpManager.ResponseCodes.OK;

public class PrioritizedTasks extends UtilHandler implements HttpHandler {

    private final TaskManager manager;
    private final Gson gson;

    public PrioritizedTasks(TaskManager manager, Gson gson) {
        this.manager = manager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if ("GET".equals(method)) {
            getPriority(exchange);
        } else {
            writeResponse(exchange, "Сервер ожидал GET запрос,а получил " + exchange.getRequestMethod(),
                    METHOD_NOT_ALLOWED.getCode());
        }

    }

    private void getPriority(HttpExchange exchange) throws IOException {
        String priority = gson.toJson(manager.getPrioritizedTasks());
        writeResponse(exchange, priority, OK.getCode());
    }
}
