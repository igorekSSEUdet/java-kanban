package managers.HttpManager.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.inMemoryManager.TaskManager;
import java.io.IOException;

public class HistoryHandler extends UtilHandler implements HttpHandler {

    private final TaskManager manager;
    private final Gson gson;

    public HistoryHandler(TaskManager manager,Gson gson) {
        this.manager = manager;
        this.gson = gson;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if ("GET".equals(method)) {
            getHistory(exchange);
        } else writeResponse(exchange,
                "Сервер ожидал GET запрос,а получил" + method,400);

    }

    private void getHistory(HttpExchange exchange) throws IOException {
        String history = gson.toJson(manager.getHistory());
        writeResponse(exchange,history,200);
    }
}
