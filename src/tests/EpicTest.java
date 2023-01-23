package tests;

import manager.InMemoryTaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpicTest {
    InMemoryTaskManager manager;

    @BeforeEach
    protected void createManager() {
         manager = new InMemoryTaskManager();

    }

    @Test
    void checkEpicsStatus() {

        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);

        Epic epic = new Epic("name4", "desc");

        Subtask subtask1 = new Subtask("nameSub1", "descSub", dateTime, 1000, epic.getId());
        Subtask subtask2 = new Subtask("nameSub2", "descSub", dateTime, 1000, epic.getId());
        Subtask subtask3 = new Subtask("nameSub3", "descSub", dateTime, 1000, epic.getId());

        manager.addEpic(epic);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addSubtask(subtask3);
        assertEquals(Status.NEW,epic.getStatus());

        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        subtask3.setStatus(Status.DONE);
        manager.updateSubtask(subtask1);
        manager.updateSubtask(subtask2);
        manager.updateSubtask(subtask3);
        assertEquals(Status.DONE,epic.getStatus());
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.NEW);
        manager.updateSubtask(subtask1);
        manager.updateSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS,epic.getStatus());

    }
    @Test
    void checkListOfSubtasksInEpic() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);

        Epic epic = new Epic("name4", "desc");

        Subtask subtask1 = new Subtask("nameSub1", "descSub", dateTime, 1000, epic.getId());
        Subtask subtask2 = new Subtask("nameSub2", "descSub", dateTime, 1000, epic.getId());
        Subtask subtask3 = new Subtask("nameSub3", "descSub", dateTime, 1000, epic.getId());


        manager.addEpic(epic);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addSubtask(subtask3);
        assertEquals(3,epic.getSubtasks().size());
        manager.removeSubtaskById(subtask1.getId());
        manager.removeSubtaskById(subtask2.getId());
        assertEquals(1,epic.getSubtasks().size());
        manager.removeSubtaskById(subtask3.getId());
        assertEquals(0,epic.getSubtasks().size());
    }

    @Test
    void checkTimeInEpic() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);
        LocalDateTime dateTime1 = LocalDateTime.of(2023, 1,
                20, 20, 15);
        LocalDateTime dateTime2 = LocalDateTime.of(2023, 1,
                20, 21, 15);


        Epic epic = new Epic("name4", "desc");

        Subtask subtask1 = new Subtask("nameSub1", "descSub", dateTime, 100, epic.getId());
        Subtask subtask2 = new Subtask("nameSub2", "descSub", dateTime1, 100, epic.getId());
        Subtask subtask3 = new Subtask("nameSub3", "descSub", dateTime2, 100, epic.getId());

        manager.addEpic(epic);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addSubtask(subtask3);

        assertEquals(epic.getStartTime(),subtask1.getStartTime());
        assertEquals(epic.getEndTime(),subtask3.getEndTime());
        assertEquals(300,epic.getDuration());

    }
}
