import manager.Managers;
import manager.Status;
import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;


public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();
        Task task1 = new Task("task1", "desc1");
        Task task2 = new Task("task2", "desc1");
        Task task3 = new Task("task3", "desc1");
        Task task4 = new Task("task4", "desc1");
        Task task5 = new Task("task5", "desc1");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addTask(task4);
        taskManager.addTask(task5);

        Epic epic = new Epic("epic", "desc");
        Epic epic1 = new Epic("epic1", "desc");
        taskManager.addEpic(epic);
        taskManager.addEpic(epic1);

        Subtask subtask1 = new Subtask("subtask1", "desc", Status.NEW, epic.getId());
        Subtask subtask2 = new Subtask("subtask2", "desc", Status.NEW, epic.getId());
        Subtask subtask3 = new Subtask("subtask3", "desc", Status.NEW, epic.getId());
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);


        System.out.println("---Tasks---");
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getTaskById(task3.getId());
        taskManager.getTaskById(task4.getId());
        System.out.println("history before sorting: " + taskManager.getHistory());
        System.out.println("size of history: " + taskManager.getHistory().size());
        taskManager.getTaskById(task4.getId());
        taskManager.getTaskById(task3.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getTaskById(task1.getId());
        System.out.println("history after sorting: " + taskManager.getHistory());
        System.out.println("size of history: " + taskManager.getHistory().size());
        taskManager.removeTaskById(task4.getId());
        System.out.println("history after remove one task: " + taskManager.getHistory());
        System.out.println("size of history: " + taskManager.getHistory().size());
        taskManager.removeAllTasks();
        System.out.println("history after remove all tasks: " + taskManager.getHistory());
        System.out.println("size of history: " + taskManager.getHistory().size());

        System.out.println("---Subtasks,Epics---");
        taskManager.getEpicById(epic.getId());
        taskManager.getEpicById(epic1.getId());
        taskManager.getSubtaskById(subtask1.getId());
        taskManager.getSubtaskById(subtask2.getId());
        taskManager.getSubtaskById(subtask3.getId());

        System.out.println(taskManager.getHistory());
        taskManager.removeSubtaskById(subtask1.getId());
        System.out.println("after remove one Subtask: " + taskManager.getHistory());
        System.out.println("size of history: " + taskManager.getHistory().size());
        taskManager.removeAllSubtasks();
        System.out.println("after remove all Subtask: " + taskManager.getHistory());
        System.out.println("size of history: " + taskManager.getHistory().size());
        taskManager.removeAllEpics();
        System.out.println("after remove all Epics: " + taskManager.getHistory());
        System.out.println("size of history: " + taskManager.getHistory().size());
    }
}
