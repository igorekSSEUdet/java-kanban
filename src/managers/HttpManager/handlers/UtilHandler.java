package managers.HttpManager.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public abstract class UtilHandler {
    protected void writeResponse(HttpExchange exchange, String response, int responseCode) throws IOException {

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
