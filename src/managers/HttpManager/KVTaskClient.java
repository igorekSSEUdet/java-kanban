package managers.HttpManager;


import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class KVTaskClient {



    private final String url;
    private final String API_TOKEN;
    private HttpClient client;
    private HttpRequest request;

    HttpResponse<String> response;


    public KVTaskClient(String serverURL) throws IOException, InterruptedException {
        this.url = serverURL;
        URI uri = URI.create(serverURL + "/register");
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        request = requestBuilder
                .uri(uri)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-Type", "application/json")
                .build();
        client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        API_TOKEN = response.body();
    }


    public void put(String key, String json) {
        URI uri = URI.create(url + "/save/" + key + "?API_TOKEN=" + API_TOKEN);
        client = HttpClient.newHttpClient();
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        request = requestBuilder
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-Type", "application/json")
                .build();
        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public JsonArray load(String key) {
        client = HttpClient.newHttpClient();
        URI uri = URI.create(url + "/load/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        request = requestBuilder
                .uri(uri)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() != 200) {
                System.out.println("ERROR STATUS CODE " + response.statusCode());
                return null;
            }
            return JsonParser.parseString(response.body()).getAsJsonArray();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
