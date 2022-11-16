package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        ArrayList<Integer> subtasksId = new ArrayList<>();
        //Tasks
        Task task = new Task("name", "desc", "NEW");
        Task task1 = new Task("name", "desc", "NEW");
        manager.addTask(task);
        manager.addTask(task1);

        System.out.println(manager.getIdTask(task.getId()));
        System.out.println(manager.getIdTask(task1.getId()));
        System.out.println(manager.getAlltasks());
        manager.removeIdTask(task.getId());
        System.out.println(manager.getAlltasks());
        manager.removeAllTasks();
        System.out.println(manager.getAlltasks());

        //Epics
        Epic epic = new Epic("name", "desc"); // created epic
        int newId = manager.addEpic(epic);

        Epic epicForUpdate = manager.getEpic(newId); // changed epic

        epicForUpdate.setName("NEW name");
        epicForUpdate.setDescription("NEW desc"); // new parameters

        manager.updateEpic(epicForUpdate); // update epic

        System.out.println(manager.getAllEpics()); // check


        Subtask subtask = new Subtask("name", "desc", "IN_PROGRESS", epic.getId());
        Subtask subtask1 = new Subtask("name", "desc", "DONE", epic.getId());
        Subtask subtask2 = new Subtask("name", "desc", "NEW", epic.getId());
        Subtask subtask3 = new Subtask("name", "desc", "IN_PROGRESS", epic.getId()); // created subtasks

        manager.addSubtask(subtask);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2); // add subtasks

        System.out.println(epic.getSubtaskIds());
        System.out.println(manager.getAllSubtasks()); // check

        System.out.println(epicForUpdate.getStatus()); // check status
        manager.removeIdSubtask(subtask.getId()); // remove first subtask
        System.out.println(epic.getSubtaskIds()); // check in list of epic
        System.out.println(manager.getAllSubtasks()); // check in map of manager
        System.out.println(epicForUpdate.getStatus()); // check status
        //manager.removeAllSubtasks(); // delete all subtasks in hashmap and list in epic
        System.out.println(epic.getSubtaskIds());
        System.out.println(manager.getAllSubtasks()); // check
        System.out.println(manager.getSubtasks(epicForUpdate.getId())); // check certain epic subtasks

    }
}
