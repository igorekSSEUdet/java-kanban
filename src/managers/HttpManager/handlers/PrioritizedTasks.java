package managers.HttpManager.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.taskManager.TaskManager;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class PrioritizedTasks implements HttpHandler {

    private final TaskManager manager;
    private final Gson gson;

    public PrioritizedTasks(TaskManager manager,Gson gson) {
        this.manager = manager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if ("GET".equals(method)) {
            getPriority(exchange);
        }

    }

    private void getPriority(HttpExchange exchange) throws IOException {
        String priority = gson.toJson(manager.getPrioritizedTasks());
        writeResponse(exchange, priority, 200);
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
}
