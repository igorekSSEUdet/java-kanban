import managers.taskManager.InMemoryTaskManager;
import model.Task;

import java.time.LocalDateTime;

import static model.Status.NEW;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task("name", "desc", NEW);
        LocalDateTime time = LocalDateTime.of(2023, 1, 25, 19, 31);
        LocalDateTime time2 = LocalDateTime.of(2022, 1, 25, 19, 31);
        LocalDateTime time3 = LocalDateTime.of(2021, 1, 25, 19, 31);
        Task task = new Task("name", "desc", NEW, time, 60L);
        Task task2 = new Task("name", "desc", NEW, time2, 60L);
        Task task3 = new Task("name", "desc", NEW, time3, 60L);
        Task task6 = new Task("name", "desc", NEW);

      taskManager.updateTask(task);
        System.out.println(taskManager.getAllTasks().size());



    }
}
