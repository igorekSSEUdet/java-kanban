package tests.managersTests;

import exceptions.TaskManagerException;
import managers.inMemoryManager.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static model.Status.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class TaskManagerTest<T extends TaskManager> {
    T manager;

    protected abstract void createManager();


    @Test
    public void getAllTasks() {
        Task task6 = new Task("name", "desc", NEW);
        Task task5 = new Task("name", "desc", NEW);
        Task task4 = new Task("name", "desc", NEW);
        manager.addTask(task6);
        manager.addTask(task5);
        manager.addTask(task4);
        assertEquals(3, manager.getAllTasks().size());
    }

    @Test
    public void updateTaskWithWrongId() {
        Task task6 = new Task("name", "desc", NEW);
        manager.addTask(task6);
        task6.setId(50);

        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> manager.updateTask(task6));
        assertEquals("Такой задачи нет", exception.getMessage());
    }

    @Test
    public void updateSubtaskWithEmptySubtasks() {
        Epic epic = new Epic("nameEpic", "subEpic");
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());

        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> manager.updateSubTask(subtask));
        assertEquals("Такой подзадачи нет", exception.getMessage());
    }

    @Test
    public void updateEpic() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        manager.updateEpic(epic);

        assertEquals(1, manager.getAllEpics().size());
    }

    @Test
    public void findEpicById() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);

        assertEquals(epic, manager.getEpicById(epic.getId()));
    }

    @Test
    public void removeAllTasks() {
        Task task6 = new Task("name", "desc", NEW);
        Task task5 = new Task("name", "desc", NEW);
        Task task4 = new Task("name", "desc", NEW);
        manager.addTask(task6);
        manager.addTask(task5);
        manager.addTask(task4);
        manager.removeAllTasks();
        assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    public void findTaskById() {
        Task task6 = new Task("name", "desc", NEW);
        manager.addTask(task6);
        assertEquals(task6, manager.getTaskById(task6.getId()));
    }

    @Test
    public void updateSubtask() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        manager.addSubTask(subtask);
        manager.updateSubTask(subtask);
        assertEquals(1, manager.getAllSubTasks().size());
    }

    @Test
    public void createNewSubtask() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        manager.addSubTask(subtask);
        assertEquals(1, manager.getAllSubTasks().size());
    }

    @Test
    public void findSubtaskById() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        manager.addSubTask(subtask);
        assertEquals(subtask, manager.getSubTaskById(subtask.getId()));
    }

    @Test
    public void removeAllEpics() {
        Epic epic = new Epic("nameEpic", "subEpic");
        Epic epic1 = new Epic("nameEpic", "subEpic");
        Epic epic2 = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        manager.addEpic(epic1);
        manager.addEpic(epic2);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        Subtask subtask1 = new Subtask("name", "desc", NEW, epic1.getId());
        Subtask subtask2 = new Subtask("name", "desc", NEW, epic2.getId());
        manager.addSubTask(subtask);
        manager.addSubTask(subtask1);
        manager.addSubTask(subtask2);
        manager.removeAllEpics();
        assertEquals(0, manager.getAllEpics().size());
        assertEquals(0, manager.getAllSubTasks().size());
    }

    @Test
    public void findTaskByIdWithWrongId() {
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> manager.getTaskById(0));
        assertEquals("Такой задачи нет", exception.getMessage());
    }

    @Test
    public void findEpicByIdWithEmptyEpics() {
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> manager.getEpicById(0));
        assertEquals("Нет такого эпика", exception.getMessage());
    }

    @Test
    public void getAllSubtasks() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        Subtask subtask1 = new Subtask("name", "desc", NEW, epic.getId());
        Subtask subtask2 = new Subtask("name", "desc", NEW, epic.getId());
        manager.addSubTask(subtask);
        manager.addSubTask(subtask1);
        manager.addSubTask(subtask2);
        assertEquals(3, manager.getAllSubTasks().size());
    }

    @Test
    public void removeEpicByIdWithEmptyEpics() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        manager.removeEpicById(epic.getId());
        assertEquals(0, manager.getAllEpics().size());
    }

    @Test
    public void updateSubtaskWithWrongId() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        manager.addSubTask(subtask);
        subtask.setId(50);

        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> manager.updateSubTask(subtask));
        assertEquals("Такой подзадачи нет", exception.getMessage());
    }

    @Test
    public void getSubtasksByEpicWithEmptyEpics() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);

        assertEquals(0, manager.getSubtasksByEpic(epic.getId()).size());
    }

    @Test
    public void updateEpicWithWrongId() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        epic.setId(50);
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> manager.updateEpic(epic));
        assertEquals("Такого эпика нет", exception.getMessage());

    }

    @Test
    public void getAllEpicsWithEmptyEpics() {

        assertEquals(0, manager.getAllEpics().size());
    }

    @Test
    public void createNewEpic() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        assertEquals(epic, manager.getEpicById(epic.getId()));
        assertEquals(1, manager.getAllEpics().size());

    }

    @Test
    public void updateTask() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        manager.addSubTask(subtask);
        subtask.setName("new");
        manager.updateSubTask(subtask);
        assertEquals(1, manager.getAllSubTasks().size());
        assertEquals(subtask, manager.getSubTaskById(subtask.getId()));
    }

    @Test
    public void removeAllSubtasksWithEmptySubtasks() {
        manager.removeAllSubTasks();
        assertEquals(0, manager.getAllSubTasks().size());
    }

    @Test
    public void findSubtaskByIdWithWrongId() {
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> manager.getSubTaskById(50));
        assertEquals("Такой подзадачи нет", exception.getMessage());
    }

    @Test
    public void removeSubtaskByIdWithEmptySubtasks() {
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> manager.removeSubTaskById(50));
        assertEquals("Такой подзадачи нет", exception.getMessage());
    }

    @Test
    public void updateEpicWithEmptyEpics() {
        Epic epic = new Epic("nameEpic", "subEpic");
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> manager.updateEpic(epic));
        assertEquals("Такого эпика нет", exception.getMessage());
    }

    @Test
    public void getAllTasksWithEmptyTasks() {
        assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    public void updateTaskWithEmptyTasks() {
        Task task6 = new Task("name", "desc", NEW);

        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> manager.updateTask(task6));
        assertEquals("Такой задачи нет", exception.getMessage());
    }

    @Test
    public void createNewTask() {
        Task task6 = new Task("name", "desc", NEW);
        manager.addTask(task6);
        assertEquals(task6, manager.getTaskById(task6.getId()));
        assertEquals(1, manager.getAllTasks().size());
    }

    @Test
    public void addTasksWithIntersection() {
        LocalDateTime time3 = LocalDateTime.of(2021, 1, 25, 19, 31);
        Task task = new Task("name", "desc", NEW, time3, 60L);
        Task task2 = new Task("name", "desc", NEW, time3, 10L);
        manager.addTask(task);
        final TaskManagerException exception = assertThrows(
                TaskManagerException.class,
                () -> manager.addTask(task2));
        assertEquals("Задачи не должны пересекаться по времени", exception.getMessage());
    }

    @Test
    public void getHistory() {
        Task task6 = new Task("name", "desc", NEW);
        manager.addTask(task6);
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        manager.addSubTask(subtask);
        manager.getTaskById(task6.getId());
        manager.getEpicById(epic.getId());
        manager.getSubTaskById(subtask.getId());
        assertEquals(3, manager.getHistory().size());
    }

    @Test
    public void findTaskByIdWithEmptyTasks() {
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> manager.getTaskById(50));
        assertEquals("Такой задачи нет", exception.getMessage());
    }

    @Test
    public void getSubtasksByEpic() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        Subtask subtask1 = new Subtask("name", "desc", NEW, epic.getId());
        Subtask subtask2 = new Subtask("name", "desc", NEW, epic.getId());
        manager.addSubTask(subtask);
        manager.addSubTask(subtask1);
        manager.addSubTask(subtask2);
        assertEquals(3, manager.getSubtasksByEpic(epic.getId()).size());
    }

    @Test
    public void findEpicByIdWithWrongId() {
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> manager.getEpicById(50));
        assertEquals("Нет такого эпика", exception.getMessage());
    }

    @Test
    public void removeAllSubtasks() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        Subtask subtask1 = new Subtask("name", "desc", NEW, epic.getId());
        Subtask subtask2 = new Subtask("name", "desc", NEW, epic.getId());
        manager.addSubTask(subtask);
        manager.addSubTask(subtask1);
        manager.addSubTask(subtask2);
        manager.removeAllSubTasks();
        assertEquals(0, manager.getAllSubTasks().size());
        assertEquals(0, manager.getSubtasksByEpic(epic.getId()).size());
    }

    @Test
    public void removeTaskById() {
        Task task6 = new Task("name", "desc", NEW);
        manager.addTask(task6);
        manager.removeTaskById(task6.getId());
        assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    public void removeSubtaskById() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        manager.addSubTask(subtask);
        manager.removeSubTaskById(subtask.getId());
        assertEquals(0, manager.getAllSubTasks().size());
    }

    @Test
    public void findSubtaskByIdWithEmptySubtasks() {
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> manager.getSubTaskById(50));
        assertEquals("Такой подзадачи нет", exception.getMessage());
    }


    @Test
    public void getAllSubtasksWithEmptySubtasks() {
        assertEquals(0, manager.getAllSubTasks().size());
    }

    @Test
    public void removeEpicById() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        manager.removeEpicById(epic.getId());
        assertEquals(0, manager.getAllEpics().size());
    }

    @Test
    public void removeAllTasksWithEmptyTasks() {
        manager.removeAllTasks();
        assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    public void getHistoryWithEmptyTasks() {

        assertEquals(0, manager.getHistory().size());
    }

    @Test
    public void removeTaskByIdWithEmptyTasks() {
        final NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> manager.removeTaskById(50));
        assertEquals("Такой задачи нет", exception.getMessage());

    }


}
