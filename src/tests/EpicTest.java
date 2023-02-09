
import managers.inMemoryManager.InMemoryTaskManager;
import managers.inMemoryManager.TaskManager;
import model.Epic;
import model.Subtask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static model.Status.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EpicTest {

    TaskManager manager;

    @BeforeEach
    protected void createManager() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void shouldShowNewForEpicWithNewSubtasks() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        Subtask subtask1 = new Subtask("name", "desc", NEW, epic.getId());
        Subtask subtask2 = new Subtask("name", "desc", NEW, epic.getId());
        manager.addSubTask(subtask);
        manager.addSubTask(subtask1);
        manager.addSubTask(subtask2);
        assertEquals(epic.getStatus(), NEW);
    }

    @Test
    void shouldShowDoneForEpicWithNewSubtasks() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", DONE, epic.getId());
        Subtask subtask1 = new Subtask("name", "desc", DONE, epic.getId());
        Subtask subtask2 = new Subtask("name", "desc", DONE, epic.getId());
        manager.addSubTask(subtask);
        manager.addSubTask(subtask1);
        manager.addSubTask(subtask2);
        assertEquals(epic.getStatus(), DONE);
    }

    @Test
    void shouldShowInProgressForEpicWithNewSubtasks() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        Subtask subtask1 = new Subtask("name", "desc", DONE, epic.getId());
        Subtask subtask2 = new Subtask("name", "desc", NEW, epic.getId());
        manager.addSubTask(subtask);
        manager.addSubTask(subtask1);
        manager.addSubTask(subtask2);
        assertEquals(epic.getStatus(), IN_PROGRESS);
    }

    @Test
    void shouldShowNewForEpicWithNoSubtasks() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        assertEquals(epic.getStatus(), NEW);
    }

    @Test
    void shouldReturnNullTimeForEpicWithSubtasksWithNoTime() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        Subtask subtask = new Subtask("name", "desc", NEW, epic.getId());
        Subtask subtask1 = new Subtask("name", "desc", DONE, epic.getId());
        Subtask subtask2 = new Subtask("name", "desc", NEW, epic.getId());
        manager.addSubTask(subtask);
        manager.addSubTask(subtask1);
        manager.addSubTask(subtask2);
        assertNull(epic.getStartTime());
    }

    @Test
    void shouldReturnStartAndEndTimeForEpicWithTwoSubtasks() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        LocalDateTime time = LocalDateTime.of(2023, 1, 25, 19, 31);
        LocalDateTime time2 = LocalDateTime.of(2022, 1, 25, 19, 31);

        Subtask subtask = new Subtask("name", "desc", NEW, time, 60L, epic.getId());
        Subtask subtask1 = new Subtask("name", "desc", NEW, time2, 60L, epic.getId());
        manager.addSubTask(subtask);
        manager.addSubTask(subtask1);
        assertEquals(epic.getStartTime(), time2);
        assertEquals(epic.getEndTime(), subtask.getEndTime());

    }

    @Test
    void shouldReturn30MinEpicForTwo15MinSubtasks() {
        Epic epic = new Epic("nameEpic", "subEpic");
        manager.addEpic(epic);
        LocalDateTime time = LocalDateTime.of(2023, 1, 25, 19, 31);
        LocalDateTime time2 = LocalDateTime.of(2022, 1, 25, 19, 31);

        Subtask subtask = new Subtask("name", "desc", NEW, time, 15L, epic.getId());
        Subtask subtask1 = new Subtask("name", "desc", NEW, time2, 15L, epic.getId());
        manager.addSubTask(subtask);
        manager.addSubTask(subtask1);
        assertEquals(epic.getDuration(), 30);


    }

}
