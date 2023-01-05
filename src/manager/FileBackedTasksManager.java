package manager;

import errors.ManagerSaveException;
import historyManager.HistoryManager;
import model.Epic;
import model.Subtask;
import model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static model.TaskType.*;


class FileBackedTasksManager extends InMemoryTaskManager {
    public static void main(String[] args) {

        File file1 = new File("src/sources/SaveData.csv");

        FileBackedTasksManager fileBackedTasksManager = loadFromFile(file1);
        Task task = new Task("name", "desc");
        Task task1 = new Task("name", "desc");
        Task task2 = new Task("name", "desc");
        Epic epic = new Epic("name", "desc");
        Subtask subtask = new Subtask("name", "desc", epic.getId());
        fileBackedTasksManager.addEpic(epic);
        fileBackedTasksManager.addSubtask(subtask);
        fileBackedTasksManager.addTask(task);
        fileBackedTasksManager.addTask(task1);
        fileBackedTasksManager.addTask(task2);
        fileBackedTasksManager.getTaskById(task.getId());
        fileBackedTasksManager.getTaskById(task1.getId());
        fileBackedTasksManager.getTaskById(task2.getId());
        fileBackedTasksManager.getEpicById(epic.getId());

        System.out.println(fileBackedTasksManager.getHistory());
    }

    File file;

    public FileBackedTasksManager() {
        this.file = (new File("src/sources/SaveData.csv"));
    }


    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();

    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();

    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();

    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();

    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();

    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();

    }

    @Override
    public void removeSubtaskById(int id) {
        super.removeSubtaskById(id);
        save();

    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();

    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();

    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();

    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();

    }

    @Override
    public List<Task> getHistory() {
        save();
        return inMemoryHistoryManager.getHistory();

    }

    static String historyToString(HistoryManager manager) {
        List<String> taskId = new ArrayList<>();
        for (Task task : manager.getHistory()) {
            taskId.add(String.valueOf(task.getId()));
        }
        return String.join(",", taskId);
    }

    static List<Integer> historyFromString(String value) {
        String[] taskId = value.split(",");
        List<Integer> idTask = new ArrayList<>();
        for (String s : taskId) {
            idTask.add(Integer.parseInt(s));
        }
        return idTask;
    }

    String toString(Task task) {
        if (task.getTaskType().equals(TASK)) {
            return String.format("%d%s%s%s%s%s%s%s%s", task.getId(), ",", task.getTaskType(), ","
                    , task.getName(), ",", task.getStatus(), ",", task.getDescription());

        } else if (task.getTaskType().equals(SUBTASK)) {
            Subtask subtask = (Subtask) task;
            return String.format("%d%s%s%s%s%s%s%s%s%s%d", subtask.getId(), ",", subtask.getTaskType(), ",",
                    subtask.getName(), ",", subtask.getStatus(), ",", subtask.getDescription(), ",", subtask.getEpicId());
        } else {
            Epic epic = (Epic) task;
            return String.format("%d%s%s%s%s%s%s%s%s", epic.getId(), ",", epic.getTaskType(), ",", epic.getName(), ",", epic.getStatus(), ",",
                    epic.getDescription());
        }
    }


    public void save() {
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.append("id,type,name,status,description,epic");
            writer.newLine();
            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                writer.append(toString(entry.getValue()));
                writer.newLine();

            }

            for (Map.Entry<Integer, Subtask> entry : subtasks.entrySet()) {
                writer.append(toString(entry.getValue()));
                writer.newLine();

            }
            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                writer.append(toString(entry.getValue()));
                writer.newLine();

            }
            writer.newLine();
            writer.append(historyToString(this.inMemoryHistoryManager));


        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения данных");
        }
    }

    Task fromString(String value) {
        if (!value.equals("")) {
            String[] line = value.split(",");
            List<Task> taskList = new ArrayList<>();
            if (line[1].equals(TASK.toString())) {
                Task task = new Task(Integer.parseInt(line[0]), line[1], line[2], line[3], line[4]);
                this.tasks.put(task.getId(), task);
                taskList.add(task);
                return task;
            } else if (line[1].equals(SUBTASK.toString())) {
                Subtask subtask = new Subtask(Integer.parseInt(line[0]), line[1], line[2],
                        line[3], line[4], Integer.parseInt(line[5]));
                this.subtasks.put(subtask.getId(), subtask);
                taskList.add(subtask);
                return subtask;
            } else if (line[1].equals(EPIC.toString())) {
                Epic epic = new Epic(Integer.parseInt(line[0]), line[1], line[2], line[3], line[4]);
                this.epics.put(epic.getId(), epic);
                taskList.add(epic);
                return epic;
            } else {
                List<Integer> list = historyFromString(String.join(",", line));
                for (Task task : taskList) {
                    for (Integer id : list) {
                        if (task.getId() == id) {
                            inMemoryHistoryManager.add(task);
                        }
                    }

                }
                return null;
            }
        } else {
            return null;
        }

    }

    static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();

        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            br.readLine();
            while (br.ready()) {
                String line = br.readLine();
                fileBackedTasksManager.fromString(line);
            }
            br.close();
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки данных");
        }
        return fileBackedTasksManager;
    }

}
