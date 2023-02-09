package httpTaskManagerTests;


import managers.HttpManager.HttpTaskManager;
import managers.HttpManager.HttpTaskServer;
import managers.HttpManager.KVServer;
import managers.fileBackedManager.FileBackedTasksManager;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static model.Status.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskManagerTest {

    HttpTaskManager manager;
    KVServer kv;
    HttpTaskServer server;

    @BeforeEach
    void startServer() {
        try {
            kv = new KVServer();
            server = new HttpTaskServer();
            kv.start();
            server.start();
            manager = new HttpTaskManager("http://localhost:8078");

        } catch (IOException | InterruptedException e) {

            throw new RuntimeException(e);
        }

    }

    @AfterEach
    void stopServer () {
        kv.stop();
        server.stop();
    }

    @Test
    void shouldRestoreTaskManagerWithSomeTasks() throws IOException, InterruptedException {
        Task task6 = new Task("name", "desc", NEW);
        Task task5 = new Task("name", "desc", NEW);
        Task task4 = new Task("name", "desc", NEW);
        manager.addTask(task6);
        manager.addTask(task5);
        manager.addTask(task4);
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        manager.addSubTask(subtask);
        manager.getTaskById(task4.getId());
        manager.getEpicById(epic.getId());
        manager.getSubTaskById(subtask.getId());

        HttpTaskManager manager2 = HttpTaskManager.loadFromServer();

        assertEquals(manager.getAllTasks(), manager2.getAllTasks());
        assertEquals(manager.getAllSubTasks(), manager2.getAllSubTasks());
        assertEquals(manager.getAllEpics(), manager2.getAllEpics());
        assertEquals(manager.getHistory().size(), manager2.getHistory().size());
    }

    @Test
    void shouldRestoreTasksWithoutHistory() throws IOException, InterruptedException {
        Task task6 = new Task("name", "desc", NEW);
        Task task5 = new Task("name", "desc", NEW);
        Task task4 = new Task("name", "desc", NEW);
        manager.addTask(task6);
        manager.addTask(task5);
        manager.addTask(task4);
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        manager.addSubTask(subtask);
        File file = new File("src/fileSources/SaveData.csv");
        FileBackedTasksManager manager2 = HttpTaskManager.loadFromServer();

        assertEquals(manager.getAllTasks(), manager2.getAllTasks());
        assertEquals(manager.getAllSubTasks(), manager2.getAllSubTasks());
        assertEquals(manager.getAllEpics(), manager2.getAllEpics());
        assertEquals(manager.getHistory().size(), manager2.getHistory().size());
    }

    @Test
    void shouldRestoreEpicWithNoSubtasks() throws IOException, InterruptedException {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        FileBackedTasksManager manager2 = HttpTaskManager.loadFromServer();
        assertEquals(manager.getAllEpics(), manager2.getAllEpics());
    }

    @Test
    void shouldRestoreEpicWithSubtasks() throws IOException, InterruptedException {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        Subtask subtask2 = new Subtask("name", "desc", NEW, epic.getId());
        Subtask subtask3 = new Subtask("name", "desc", NEW, epic.getId());
        manager.addSubTask(subtask);
        manager.addSubTask(subtask2);
        manager.addSubTask(subtask3);
        FileBackedTasksManager manager2 = HttpTaskManager.loadFromServer();
        assertEquals(manager.getAllEpics(), manager2.getAllEpics());

    }
}
