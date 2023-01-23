package tests.managers;

import manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    @Override
    protected void createManager() {
        manager = new InMemoryTaskManager();

    }





}