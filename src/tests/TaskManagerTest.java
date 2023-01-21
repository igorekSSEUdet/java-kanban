package tests;

import manager.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class TaskManagerTest<T extends TaskManager> {

    T manager;

    protected abstract void createManager();

    @Test
    void addTask() {

        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);
        Task task = new Task("nameSub1", "descSub", dateTime, 1000);
        manager.addTask(task);
        assertEquals(task, manager.getTaskById(task.getId()));
        assertNotNull(manager.getTaskById(task.getId()));
        assertEquals(1, manager.getAllTasks().size());
    }

    @Test
    void getTaskById() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);
        Task task = new Task("nameSub1", "descSub", dateTime, 1000);
        manager.addTask(task);

        assertNotNull(manager.getTaskById(task.getId()));
        assertEquals(task, manager.getTaskById(task.getId()));

    }

    @Test
    void updateTask() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);
        Task task = new Task("nameSub1", "descSub", dateTime, 1000);
        manager.addTask(task);
        task.setName("upd");
        manager.updateTask(task);
        assertEquals(1, manager.getAllTasks().size());
        assertEquals(task, manager.getTaskById(task.getId()));
    }

    @Test
    void removeAllTasks() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);
        Task task = new Task("nameSub1", "descSub", dateTime, 1000);
        manager.addTask(task);
        manager.removeAllTasks();
        assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    void removeTaskById() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);
        Task task = new Task("nameSub1", "descSub", dateTime, 1000);
        manager.addTask(task);
        manager.removeTaskById(task.getId());
        assertEquals(0, manager.getAllTasks().size());

    }


    @Test
    void getAllTasks() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);
        Task task = new Task("nameSub1", "descSub", dateTime, 1000);
        manager.addTask(task);
        List<Task> taskList = manager.getAllTasks();
        assertEquals(taskList, manager.getAllTasks());

    }

    @Test
    void addEpic() {
        Epic epic = new Epic("name4", "desc");
        manager.addEpic(epic);
        assertEquals(1, manager.getAllEpics().size());
        assertEquals(epic, manager.getEpicById(epic.getId()));

    }


    @Test
    void removeAllEpics() {
        Epic epic = new Epic("name4", "desc");
        manager.addEpic(epic);
        manager.removeAllEpics();
        assertEquals(0, manager.getAllEpics().size());
        assertNull(manager.getEpicById(epic.getId()));
    }

    @Test
    void removeEpicById() {
        Epic epic = new Epic("name4", "desc");
        manager.addEpic(epic);
        manager.removeEpicById(epic.getId());
        assertEquals(0, manager.getAllEpics().size());
        assertNull(manager.getEpicById(epic.getId()));
    }

    @Test
    void getEpicById() {
        Epic epic = new Epic("name4", "desc");
        manager.addEpic(epic);
        assertEquals(epic, manager.getEpicById(epic.getId()));
        assertNotNull((manager.getEpicById(epic.getId())));
    }

    @Test
    void updateEpic() {
        Epic epic = new Epic("name4", "desc");
        manager.addEpic(epic);
        epic.setName("upd");
        manager.updateEpic(epic);
        assertEquals(epic, manager.getEpicById(epic.getId()));
        assertEquals(epic.getName(), manager.getEpicById(epic.getId()).getName());

    }

    @Test
    void getAllEpics() {
        Epic epic = new Epic("name4", "desc");
        Epic epic1 = new Epic("name4", "desc");
        Epic epic2 = new Epic("name4", "desc");
        manager.addEpic(epic);
        manager.addEpic(epic1);
        manager.addEpic(epic2);

        ArrayList<Epic> epics = manager.getAllEpics();
        assertEquals(epics, manager.getAllEpics());
        assertEquals(3, manager.getAllEpics().size());

    }

    @Test
    void getSubtasksByEpicId() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);
        Epic epic = new Epic("name4", "desc");
        Subtask subtask = new Subtask("nameSub2", "descSub", dateTime, 1000, epic.getId());
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        ArrayList<Subtask> epicsSubtasks = manager.getSubtasksByEpicId(epic.getId());
        assertEquals(epicsSubtasks,manager.getSubtasksByEpicId(epic.getId()));

    }

    @Test
    void addSubtask() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);
        Epic epic = new Epic("name4", "desc");
        Subtask subtask = new Subtask("nameSub2", "descSub", dateTime, 1000, epic.getId());
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        assertEquals(subtask,manager.getSubtaskById(subtask.getId()));
        assertNotNull(manager.getAllSubtasks());
        assertEquals(1,manager.getAllSubtasks().size());
    }

    @Test
    void removeAllSubtasks() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);
        Epic epic = new Epic("name4", "desc");
        Subtask subtask = new Subtask("nameSub2", "descSub", dateTime, 1000, epic.getId());
        Subtask subtask1 = new Subtask("nameSub2", "descSub", dateTime, 1000, epic.getId());
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.addSubtask(subtask1);
        manager.removeAllSubtasks();

        assertEquals(0,manager.getAllSubtasks().size());
    }

    @Test
    void removeSubtaskById() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);
        Epic epic = new Epic("name4", "desc");
        Subtask subtask = new Subtask("nameSub2", "descSub", dateTime, 1000, epic.getId());
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.removeSubtaskById(subtask.getId());
        assertEquals(0,manager.getAllSubtasks().size());
        assertNull(manager.getSubtaskById(subtask.getId()));
    }

    @Test
    void getSubtaskById() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);
        Epic epic = new Epic("name4", "desc");
        Subtask subtask = new Subtask("nameSub2", "descSub", dateTime, 1000, epic.getId());
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        assertEquals(subtask,manager.getSubtaskById(subtask.getId()));
    }

    @Test
    void updateSubtask() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);
        Epic epic = new Epic("name4", "desc");
        Subtask subtask = new Subtask("nameSub2", "descSub", dateTime, 1000, epic.getId());
        manager.addEpic(epic);
        manager.addSubtask(subtask);
       subtask.setName("upd");
        manager.updateSubtask(subtask);
        //assertEquals(subtask,manager.getSubtaskById(subtask.getId()));
        assertEquals(1,manager.getAllSubtasks().size());
        assertEquals("upd",manager.getSubtaskById(subtask.getId()).getName());
    }

    @Test
    void getAllSubtasks() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);
        Epic epic = new Epic("name4", "desc");
        Subtask subtask = new Subtask("nameSub2", "descSub", dateTime, 1000, epic.getId());
        Subtask subtask1 = new Subtask("nameSub2", "descSub", dateTime, 1000, epic.getId());
        Subtask subtask2 = new Subtask("nameSub2", "descSub", dateTime, 1000, epic.getId());
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        ArrayList<Subtask> subtasks = manager.getAllSubtasks();
        assertEquals(subtasks,manager.getAllSubtasks());
    }

    @Test
    void getHistory() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);
        Task task = new Task("nameSub1", "descSub", dateTime, 1000);
        Task task2 = new Task("nameSub1", "descSub", dateTime, 1000);
        Task task3 = new Task("nameSub1", "descSub", dateTime, 1000);
        manager.addTask(task);
        manager.addTask(task2);
        manager.addTask(task3);

        manager.getTaskById(task.getId());
        manager.getTaskById(task2.getId());
        manager.getTaskById(task3.getId());

        List<Task> history = manager.getHistory();
        assertEquals(history,manager.getHistory());
        assertEquals(3,manager.getHistory().size());
    }
}
