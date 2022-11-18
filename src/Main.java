import manager.Manager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        //Tasks
        System.out.println("\uD83D\uDC47TASKS\uD83D\uDC47");
        Task task1 = new Task("name1", "desc1");
        manager.addTask(task1);
        Task task = new Task("name", "desc");
        int newId = manager.addTask(task);
        System.out.println("get task for id: " + manager.getTaskById(task.getId()));
        System.out.println("all tasks: " + manager.getAllTasks());
        Task taskForUpdate = manager.getTaskById(newId);
        taskForUpdate.setName("NAME");
        taskForUpdate.setDescription("DESCRIPTION");
        manager.updateTask(taskForUpdate);
        System.out.println("get updateTask by id: " + manager.getTaskById(taskForUpdate.getId()));
        System.out.println("all tasks: " + manager.getAllTasks());
        manager.removeTaskById(task1.getId());
        System.out.println("all tasks after remove: " + manager.getAllTasks());
        manager.removeAllTasks();
        System.out.println("all tasks after everything remove: " + manager.getAllTasks());

        //Epics
        System.out.println("\uD83D\uDC47EPICS\uD83D\uDC47");
        Epic epic = new Epic("name", "desc");
        manager.addEpic(epic);
        System.out.println(manager.getEpicById(epic.getId()));
        System.out.println("all epics before update: " + manager.getAllEpics());
        int newId1 = epic.getId();
        Epic epicForUpdate = manager.getEpicById(newId1);
        epicForUpdate.setName("NAME");
        epicForUpdate.setDescription("DESCRIPTION");
        manager.updateEpic(epicForUpdate);
        System.out.println("all epics after update: " + manager.getAllEpics());

        Epic epic1 = new Epic("name", "desc");
        manager.addEpic(epic);
        System.out.println("all epics before remove by id: " + manager.getAllEpics());
        manager.removeEpicById(epicForUpdate.getId());
        System.out.println("all epics after remove by id: " + manager.getAllEpics());
        manager.removeAllEpics();
        System.out.println("all epics after everything removed: " + manager.getAllEpics());

        //Subtasks
        System.out.println("\uD83D\uDC47SUBTASKS\uD83D\uDC47");
        Epic epicForSubtask = new Epic("name", "desc");
        Epic epicForSubtask1 = new Epic("name", "desc");

        manager.addEpic(epicForSubtask);
        manager.addEpic(epicForSubtask1);

        Subtask subtask = new Subtask("name", "desc", "NEW", epicForSubtask.getId());
        Subtask subtask1 = new Subtask("name", "desc", "DONE", epicForSubtask.getId());
        Subtask subtask2 = new Subtask("name", "desc", "NEW", epicForSubtask.getId());

        int id = manager.addSubtask(subtask);
        int id1 = manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);

        System.out.println("check epics status: " + epicForSubtask.getStatus());
        Subtask subtaskForUpdate = manager.getSubtaskById(id);
        subtaskForUpdate.setName("newNAME");
        subtaskForUpdate.setDescription("newDESCRIPTION");
        manager.updateSubtask(subtaskForUpdate);
        Subtask subtaskForUpdate1 = manager.getSubtaskById(id1);
        subtaskForUpdate1.setName("newNAME");
        subtaskForUpdate1.setDescription("newDESCRIPTION");
        manager.updateSubtask(subtaskForUpdate1);
        System.out.println(manager.getAllSubtasks());
        System.out.println("get subtask by id: " + manager.getSubtaskById(subtaskForUpdate.getId()));
        System.out.println("all subtasks: " + manager.getAllSubtasks());
        System.out.println("subtasks by epics id: " + manager.getSubtasksByEpicId(subtaskForUpdate.getEpicId()));
        System.out.println("subtasks by epics id: " + manager.getSubtasksByEpicId(epicForSubtask1.getId()));
        manager.removeSubtaskById(subtaskForUpdate.getId());
        System.out.println("all subtasks after remove by id: " + manager.getAllSubtasks());
        manager.removeAllSubtasks();
        System.out.println("after remove all subtasks \uD83D\uDC47 ");
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());
        manager.removeAllEpics();
        System.out.println("after remove all epics \uD83D\uDC47 ");
        System.out.println(manager.getAllEpics());

        //ERRORS
        System.out.println("\uD83D\uDC47ERRORS\uD83D\uDC47");
        System.out.println(manager.getSubtaskById(1));
        System.out.println(manager.getEpicById(1));
        System.out.println(manager.getTaskById(1));
        System.out.println(manager.getSubtasksByEpicId(1));
        System.out.println(manager.getAllSubtasks());
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());

    }
}
