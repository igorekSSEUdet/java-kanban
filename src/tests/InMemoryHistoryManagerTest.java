package tests;

import historyManager.InMemoryHistoryManager;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    InMemoryHistoryManager manager;

    @BeforeEach
    void createInMemoryHistoryManager() {
        manager = new InMemoryHistoryManager();
    }

    @Test
    void add() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);
        Task task = new Task("nameSub1", "descSub", dateTime, 1000);
        task.setId(0);
        manager.add(task);
        assertEquals(1,manager.getHistory().size());

        Epic epic = new Epic("name4", "desc");
        Subtask subtask = new Subtask("nameSub2", "descSub", dateTime, 1000, epic.getId());
        epic.setId(1);
        subtask.setId(2);

        manager.add(epic);
        manager.add(subtask);
        assertEquals(3,manager.getHistory().size());

        manager.add(task);
        manager.add(epic);
        manager.add(subtask);

        List<Task> history = manager.getHistory();//check absence of tasks
        assertEquals(3,manager.getHistory().size());
        assertEquals(history,manager.getHistory());

        manager.remove(subtask.getId());
        manager.remove(epic.getId());//check remove
        manager.remove(task.getId());

        List<Task> newHistory = manager.getHistory();
        assertEquals(0,manager.getHistory().size());
        assertEquals(newHistory,manager.getHistory());// also check getHistory


    }



}