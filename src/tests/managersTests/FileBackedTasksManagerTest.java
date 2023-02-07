package tests.managersTests;

import managers.fileBackedManager.FileBackedTasksManager;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static managers.fileBackedManager.FileBackedTasksManager.loadFromFile;
import static model.Status.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    @BeforeEach
    @Override
    protected void createManager() {
        manager = new FileBackedTasksManager();
    }


    @Test
    void shouldRestoreTaskManagerWithSomeTasks() {
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
        File file = new File("src/fileSources/SaveData.csv");
        FileBackedTasksManager manager2 = loadFromFile(file);

        assertEquals(manager.getAllTasks(), manager2.getAllTasks());
        assertEquals(manager.getAllSubTasks(), manager2.getAllSubTasks());
        assertEquals(manager.getAllEpics(), manager2.getAllEpics());
        assertEquals(manager.getHistory(), manager2.getHistory());
    }

    @Test
    void shouldRestoreTasksWithoutHistory() {
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
        FileBackedTasksManager manager2 = loadFromFile(file);

        assertEquals(manager.getAllTasks(), manager2.getAllTasks());
        assertEquals(manager.getAllSubTasks(), manager2.getAllSubTasks());
        assertEquals(manager.getAllEpics(), manager2.getAllEpics());
        assertEquals(manager.getHistory(), manager2.getHistory());
    }

    @Test
    void shouldRestoreEpicWithNoSubtasks() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        File file = new File("src/fileSources/SaveData.csv");
        FileBackedTasksManager manager2 = loadFromFile(file);
        assertEquals(manager.getAllEpics(), manager2.getAllEpics());
    }

    @Test
    void shouldRestoreEpicWithSubtasks() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        Subtask subtask2 = new Subtask("name", "desc", NEW, epic.getId());
        Subtask subtask3 = new Subtask("name", "desc", NEW, epic.getId());
        manager.addSubTask(subtask);
        manager.addSubTask(subtask2);
        manager.addSubTask(subtask3);
        File file = new File("src/fileSources/SaveData.csv");
        FileBackedTasksManager manager2 = loadFromFile(file);
        assertEquals(manager.getAllEpics(), manager2.getAllEpics());

    }

}


