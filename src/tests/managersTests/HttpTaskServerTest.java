package tests.managersTests;

import com.google.gson.Gson;
import managers.HttpManager.HttpTaskServer;
import managers.HttpManager.KVServer;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static model.Status.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class HttpTaskServerTest {

    KVServer kvServer;
    HttpTaskServer server;
    HttpClient client;
    HttpRequest request;


    @BeforeEach
    protected void createManager() {
        try {
            kvServer = new KVServer();
            server = new HttpTaskServer();
            server.start();
            kvServer.start();
            client = HttpClient.newHttpClient();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @AfterEach
    public void stopServer() {

        kvServer.stop();
        server.stop();
    }

    @Test
    void shouldAddTask() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        Task task = new Task("name", "desc", NEW);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task)))
                .build();

        HttpResponse<String> postResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse.statusCode());
        assertEquals(1, server.manager.getAllTasks().size());

    }

    @Test
    void shouldUpdateTask() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        Task task = new Task("name", "desc", NEW);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task)))
                .build();

        HttpResponse<String> postResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        task = server.manager.getTaskById(0);
        task.setName("newName");
        task.setDescription("newDesc");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task)))
                .build();
        HttpResponse<String> postResponse1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse1.statusCode());
        assertEquals(1, server.manager.getAllTasks().size());
        assertEquals("newName", server.manager.getTaskById(task.getId()).getName());

    }

    @Test
    void shouldDeleteTaskById() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        Task task = new Task("name", "desc", NEW);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task)))
                .build();

        HttpResponse<String> postResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse.statusCode());
        assertEquals(1, server.manager.getAllTasks().size());
        task = server.manager.getTaskById(0);
        URI url1 = URI.create("http://localhost:8080/tasks/task/?id=" + task.getId());
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .DELETE()
                .build();
        HttpResponse<String> postResponse1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse1.statusCode());
        assertEquals(0, server.manager.getAllTasks().size());

    }

    @Test
    void shouldDeleteAllTasks() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        Task task = new Task("name", "desc", NEW);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task)))
                .build();

        HttpResponse<String> postResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse.statusCode());
        assertEquals(1, server.manager.getAllTasks().size());
        task = server.manager.getTaskById(0);
        URI url1 = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .DELETE()
                .build();
        HttpResponse<String> postResponse1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse1.statusCode());
        assertEquals(0, server.manager.getAllTasks().size());

    }

    @Test
    void shouldGetTaskById() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        Task task = new Task("name", "desc", NEW);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task)))
                .build();

        HttpResponse<String> postResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse.statusCode());
        assertEquals(1, server.manager.getAllTasks().size());

        task = server.manager.getTaskById(0);
        URI url1 = URI.create("http://localhost:8080/tasks/task/?id=0");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .GET()
                .build();
        HttpResponse<String> postResponse1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(gson.fromJson(postResponse1.body(), Task.class).getName(), task.getName());
        assertEquals(gson.fromJson(postResponse1.body(), Task.class).getDescription(), task.getDescription());
        assertEquals(gson.fromJson(postResponse1.body(), Task.class).getId(), task.getId());
    }

    @Test
    void shouldGetAllTasks() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        Task task = new Task("name", "desc", NEW);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task)))
                .build();

        HttpResponse<String> postResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse.statusCode());
        assertEquals(1, server.manager.getAllTasks().size());

        task = server.manager.getTaskById(0);
        URI url1 = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .GET()
                .build();
        HttpResponse<String> postResponse1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertFalse(postResponse1.body().isEmpty());
    }

    //EPICS
    @Test
    void shouldAddEpic() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        Epic epic = new Epic("nameEpic", "subEpic");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic)))
                .build();

        HttpResponse<String> postResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse.statusCode());
        assertEquals(1, server.manager.getAllEpics().size());

    }

    @Test
    void shouldUpdateEpic() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        Epic epic = new Epic("nameEpic", "subEpic");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic)))
                .build();

        HttpResponse<String> postResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        epic = server.manager.getEpicById(0);
        epic.setName("newName");
        epic.setDescription("newDesc");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic)))
                .build();
        HttpResponse<String> postResponse1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse1.statusCode());
        assertEquals(1, server.manager.getAllEpics().size());
        assertEquals("newName", server.manager.getEpicById(epic.getId()).getName());

    }

    @Test
    void shouldDeleteEpicById() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        Epic epic = new Epic("nameEpic", "subEpic");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic)))
                .build();

        HttpResponse<String> postResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse.statusCode());
        assertEquals(1, server.manager.getAllEpics().size());
        epic = server.manager.getEpicById(0);
        URI url1 = URI.create("http://localhost:8080/tasks/epic/?id=" + epic.getId());
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .DELETE()
                .build();
        HttpResponse<String> postResponse1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse1.statusCode());
        assertEquals(0, server.manager.getAllEpics().size());

    }

    @Test
    void shouldGetAllEpics() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        Epic epic = new Epic("nameEpic", "subEpic");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic)))
                .build();

        HttpResponse<String> postResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse.statusCode());
        assertEquals(1, server.manager.getAllEpics().size());

        epic = server.manager.getEpicById(0);
        URI url1 = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .GET()
                .build();
        HttpResponse<String> postResponse1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertFalse(postResponse1.body().isEmpty());
    }

    @Test
    void shouldAddSubtask() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        Epic epic = new Epic("nameEpic", "subEpic");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic)))
                .build();

        HttpResponse<String> postResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse.statusCode());
        assertEquals(1, server.manager.getAllEpics().size());
        epic = server.manager.getEpicById(0);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        URI url1 = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subtask)))
                .build();
        HttpResponse<String> postResponse1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse1.statusCode());
        assertEquals(1, server.manager.getAllSubTasks().size());
    }

    @Test
    void shouldUpdateSubtask() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        Epic epic = new Epic("nameEpic", "subEpic");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic)))
                .build();

        HttpResponse<String> postResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        URI url1 = URI.create("http://localhost:8080/tasks/subtask");
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url1)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subtask)))
                .build();

        HttpResponse<String> postResponse2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        subtask = server.manager.getSubTaskById(1);
        subtask.setName("newName");
        subtask.setDescription("newDesc");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subtask)))
                .build();
        HttpResponse<String> postResponse1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse1.statusCode());
        assertEquals(1, server.manager.getAllSubTasks().size());
        assertEquals("newName", server.manager.getSubTaskById(subtask.getId()).getName());

    }

    @Test
    void shouldGetSubtaskById() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        Epic epic = new Epic("nameEpic", "subEpic");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic)))
                .build();

        HttpResponse<String> postResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        URI url1 = URI.create("http://localhost:8080/tasks/subtask");
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url1)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subtask)))
                .build();

        HttpResponse<String> postResponse2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        URI url2 = URI.create("http://localhost:8080/tasks/subtask/?id=1");
        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(url2)
                .GET()
                .build();

        HttpResponse<String> postResponse3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse3.statusCode());
        assertEquals(subtask.getName(), gson.fromJson(postResponse3.body(), Subtask.class).getName());
        assertEquals(subtask.getDescription(), gson.fromJson(postResponse3.body(), Subtask.class).getDescription());

    }

    @Test
    void shouldGetAllSubtasks() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        Epic epic = new Epic("nameEpic", "subEpic");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic)))
                .build();

        HttpResponse<String> postResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        URI url1 = URI.create("http://localhost:8080/tasks/subtask");
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url1)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subtask)))
                .build();

        HttpResponse<String> postResponse2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        URI url2 = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(url2)
                .GET()
                .build();

        HttpResponse<String> postResponse3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
        assertFalse(postResponse3.body().isEmpty());

    }

    @Test
    void shouldDeleteAllSubtasks() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        Epic epic = new Epic("nameEpic", "subEpic");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic)))
                .build();

        HttpResponse<String> postResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        URI url1 = URI.create("http://localhost:8080/tasks/subtask");
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url1)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subtask)))
                .build();

        HttpResponse<String> postResponse2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        URI url2 = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(url2)
                .DELETE()
                .build();

        HttpResponse<String> postResponse3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
        assertEquals(0, server.manager.getAllSubTasks().size());

    }


    @Test
    void shouldDeleteSubtaskById() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        Epic epic = new Epic("nameEpic", "subEpic");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic)))
                .build();

        HttpResponse<String> postResponse = client.send(request, HttpResponse.BodyHandlers.ofString());


        URI url1 = URI.create("http://localhost:8080/tasks/subtask");
        Subtask subtask = new Subtask("name", "desc", NEW, 0);

        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url1)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subtask)))
                .build();

        HttpResponse<String> postResponse1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse1.statusCode());
        assertEquals(1, server.manager.getAllSubTasks().size());
        subtask = server.manager.getSubTaskById(1);
        URI url2 = URI.create("http://localhost:8080/tasks/subtask/?id=" + subtask.getId());
        HttpRequest request4 = HttpRequest.newBuilder()
                .uri(url2)
                .DELETE()
                .build();
        HttpResponse<String> postResponse3 = client.send(request4, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,postResponse3.statusCode());
        assertEquals(0, server.manager.getAllSubTasks().size());

    }
}
