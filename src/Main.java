import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.manager.Manager;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        //Tasks
        Task task = new Task("name", "desc", "NEW");
        Task task1 = new Task("name", "desc", "NEW");
        manager.addTask(task);
        manager.addTask(task1);

        System.out.println(manager.getIdTask(task.getNumber()));
        System.out.println(manager.getIdTask(task1.getNumber()));
        System.out.println(manager.getAlltasks());
        manager.removeIdTask(task.getNumber());
        System.out.println(manager.getAlltasks());
        manager.removeAllTasks();
        System.out.println(manager.getAlltasks());

        //Epics
        Epic epic = new Epic("name", "desk");
        manager.addEpic(epic);

        Subtask subtask = new Subtask("name", "desk");
        manager.addSubtask(subtask);

        Subtask subtask1 = new Subtask("name1", "desk1");
        manager.addSubtask(subtask1);

        ArrayList<Subtask> list = new ArrayList<>();
        list.add(subtask);
        list.add(subtask1);

        epic = new Epic(epic.getName(), epic.getDescription(), epic.getStatus(), epic.getNumber(), list);
        manager.updateEpic(epic);

        System.out.println(epic.getSubtasks());
        System.out.println("Статус: " + epic.getStatus());
        Subtask updateSubtask = new Subtask(subtask.getName(), subtask.getDescription(), "IN_PROGRESS"
                , epic.getNumber(), subtask.getNumber());

        Subtask updateSubtask1 = new Subtask(subtask1.getName(), subtask1.getDescription(), "DONE", epic.getNumber()
                , subtask1.getNumber());
        list.clear();
        list.add(updateSubtask);
        list.add(updateSubtask1);
        manager.updateSubtask(updateSubtask);
        manager.updateSubtask(updateSubtask1);
        manager.updateEpic(epic);
        System.out.println(manager.getAllSubtasks());
        System.out.println(epic.getSubtasks());
        System.out.println("Статус: " + epic.getStatus());

    }
}
