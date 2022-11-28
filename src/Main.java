
import manager.Managers;
import manager.Status;
import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        Task task1 = new Task("name1", "desc1");
        Task task2 = new Task("name1", "desc1");
        Task task3 = new Task("name1", "desc1");
        Task task4 = new Task("name1", "desc1");
        Task task5 = new Task("name1", "desc1");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addTask(task4);
        taskManager.addTask(task5);

        Epic epic = new Epic("name", "desc");
        Epic epic1 = new Epic("name", "desc");
        taskManager.addEpic(epic);
        taskManager.addEpic(epic1);

        Subtask subtask1 = new Subtask("name", "desc", Status.NEW, epic.getId());
        Subtask subtask2 = new Subtask("name", "desc", Status.NEW, epic.getId());
        Subtask subtask3 = new Subtask("name", "desc", Status.NEW, epic.getId());
        Subtask subtask4 = new Subtask("name", "desc", Status.NEW, epic.getId());
        Subtask subtask5 = new Subtask("name", "desc", Status.NEW, epic.getId());
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);
        taskManager.addSubtask(subtask4);
        taskManager.addSubtask(subtask5);

        taskManager.getEpicById(epic.getId());
        taskManager.getEpicById(epic1.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getTaskById(task3.getId());
        taskManager.getTaskById(task4.getId());
        taskManager.getTaskById(task5.getId());
        taskManager.getEpicById(epic1.getId());
        taskManager.getTaskById(task1.getId());

        System.out.println(taskManager.getHistory());

        taskManager.getTaskById(task4.getId());
        taskManager.getTaskById(task5.getId());
        taskManager.getSubtaskById(subtask1.getId());
        taskManager.getSubtaskById(subtask2.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getTaskById(task3.getId());

        System.out.println(taskManager.getHistory());
        taskManager.addEpic(epic);


    }
}
