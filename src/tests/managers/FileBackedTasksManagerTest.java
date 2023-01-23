package tests.managers;

import manager.FileBackedTasksManager;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;
import static manager.FileBackedTasksManager.loadFromFile;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    @BeforeEach
    @Override
    protected void createManager() {
        manager = new FileBackedTasksManager();

    }
    @Test
    void checkLoadFromFile() {
        File file = new File("src/sources/SaveData.csv");

        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);
        LocalDateTime dateTime1 = LocalDateTime.of(2023, 1,
                20, 20, 15);
        LocalDateTime dateTime2 = LocalDateTime.of(2023, 1,
                20, 21, 15);
        LocalDateTime dateTime5 = LocalDateTime.of(2021, 1,
                20, 21, 15);


        Task task = new Task("nameSub1", "descSub", dateTime5, 1000);
        Epic epic = new Epic("name4", "desc");

        Subtask subtask1 = new Subtask("nameSub1", "descSub", dateTime, 1000, epic.getId());

        Subtask subtask2 = new Subtask("nameSub2", "descSub", dateTime1, 1000, epic.getId());
        Subtask subtask3 = new Subtask("nameSub3", "descSub", dateTime2, 1000, epic.getId());

        manager.addEpic(epic);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addSubtask(subtask3);
        manager.addTask(task);

        manager.getEpicById(epic.getId());
        manager.getTaskById(task.getId());
        manager.getSubtaskById(subtask1.getId());
        manager.getSubtaskById(subtask2.getId());
        manager.getSubtaskById(subtask3.getId());

        FileBackedTasksManager newManager = loadFromFile(file);
        assertEquals(manager.getAllTasks(),newManager.getAllTasks());
        assertEquals(manager.getAllEpics(),newManager.getAllEpics());
        assertEquals(manager.getAllSubtasks(),newManager.getAllSubtasks());
        assertEquals(manager.getHistory(),newManager.getHistory());
    }
}
