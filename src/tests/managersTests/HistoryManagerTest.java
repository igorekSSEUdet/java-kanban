package tests.managersTests;

import managers.historyManager.HistoryManager;
import managers.historyManager.InMemoryHistoryManager;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Status.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class HistoryManagerTest {


    HistoryManager manager;

    @BeforeEach
    protected void createManager() {
        manager = new InMemoryHistoryManager();
    }

    @Test
    void addWithDuplication() {
        Task task6 = new Task("name", "desc", NEW);
        task6.setId(1);
        manager.add(task6);
        manager.add(task6);
        manager.add(task6);
        assertEquals(1, manager.getHistory().size());

    }

    @Test
    void remove() {
        Task task6 = new Task("name", "desc", NEW);
        Task task5 = new Task("name", "desc", NEW);
        task6.setId(1);
        task5.setId(2);
        manager.add(task6);
        manager.add(task5);
        manager.remove(task5.getId());
        assertEquals(1, manager.getHistory().size());

    }

    @Test
    void getEmptyHistory() {
        assertEquals(0, manager.getHistory().size());
    }

    @Test
    void removeFromStart() {
        Task task6 = new Task("name", "desc", NEW);
        Task task5 = new Task("name", "desc", NEW);
        Task task7 = new Task("name", "desc", NEW);
        task6.setId(1);
        task5.setId(2);
        task7.setId(3);
        manager.add(task6);
        manager.add(task5);
        manager.add(task7);
        manager.remove(task6.getId());
        assertEquals(task5, manager.getHistory().get(0));
        assertEquals(task7, manager.getHistory().get(1));

    }

    @Test
    void removeFromEnd() {
        Task task6 = new Task("name", "desc", NEW);
        Task task5 = new Task("name", "desc", NEW);
        Task task7 = new Task("name", "desc", NEW);
        task6.setId(1);
        task5.setId(2);
        task7.setId(3);
        manager.add(task6);
        manager.add(task5);
        manager.add(task7);
        manager.remove(task7.getId());
        assertEquals(task6, manager.getHistory().get(0));
        assertEquals(task5, manager.getHistory().get(1));

    }


    @Test
    void removeFromMiddle() {
        Task task6 = new Task("name", "desc", NEW);
        Task task5 = new Task("name", "desc", NEW);
        Task task7 = new Task("name", "desc", NEW);
        task6.setId(1);
        task5.setId(2);
        task7.setId(3);
        manager.add(task6);
        manager.add(task5);
        manager.add(task7);
        manager.remove(task5.getId());
        assertEquals(task6, manager.getHistory().get(0));
        assertEquals(task7, manager.getHistory().get(1));

    }

    @Test
    void getHistory() {
        Task task6 = new Task("name", "desc", NEW);
        Task task5 = new Task("name", "desc", NEW);
        Task task7 = new Task("name", "desc", NEW);
        task6.setId(1);
        task5.setId(2);
        task7.setId(3);
        manager.add(task6);
        manager.add(task5);
        manager.add(task7);
        assertEquals(3, manager.getHistory().size());

    }

}
