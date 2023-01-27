package tests.managersTests;

import managers.taskManager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeEach
    @Override
    protected void createManager() {
        manager = new InMemoryTaskManager();
    }
}
