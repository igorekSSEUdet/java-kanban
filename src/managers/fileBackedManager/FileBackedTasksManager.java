package managers.fileBackedManager;


import com.google.gson.Gson;
import exceptions.ManagerSaveException;
import managers.historyManager.HistoryManager;
import managers.historyManager.InMemoryHistoryManager;
import managers.taskManager.InMemoryTaskManager;
import model.*;


import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static model.Status.NEW;
import static model.TaskType.*;

public class FileBackedTasksManager extends InMemoryTaskManager {
    public static void main(String[] args) {
        Gson gson = new Gson();
        FileBackedTasksManager tasksManager = new FileBackedTasksManager();
        Task task1 = new Task("name", "desc", NEW);
        tasksManager.addTask(task1);
        LocalDateTime time = LocalDateTime.of(2023, 1, 25, 19, 31);
        LocalDateTime time2 = LocalDateTime.of(2022, 1, 25, 19, 31);
        LocalDateTime time3 = LocalDateTime.of(2021, 1, 25, 19, 31);
        Task task = new Task("name", "desc", NEW, time, 60L);
        Task task2 = new Task("name", "desc", NEW, time2, 60L);
        Task task3 = new Task("name", "desc", NEW, time3, 60L);
        tasksManager.addTask(task);
        Task task6 = new Task("name", "desc", NEW);
        tasksManager.addTask(task6);
        tasksManager.addTask(task2);
        tasksManager.addTask(task3);

        Epic epic = new Epic("nameEpic", "subEpic");
        tasksManager.addEpic(epic);

        Subtask subtask = new Subtask("name", "desc", NEW, time, 60L, epic.getId());
        Subtask subtask1 = new Subtask("name", "desc", NEW, time2, 60L, epic.getId());
        Subtask subtask2 = new Subtask("name", "desc", NEW, time3, 60L, epic.getId());
        tasksManager.addSubTask(subtask);
        tasksManager.addSubTask(subtask1);
        tasksManager.addSubTask(subtask2);
        System.out.println();

        tasksManager.getTaskById(task.getId());
        tasksManager.getEpicById(epic.getId());
        tasksManager.getSubTaskById(subtask1.getId());
        File file = new File("src/fileSources/SaveData.csv");
        FileBackedTasksManager tasksManager2 = loadFromFile(file);
        System.out.println(tasksManager.getPrioritizedTasks());
        System.out.println(tasksManager.getPrioritizedTasks().equals(tasksManager2.getPrioritizedTasks()));
        System.out.println(tasksManager2.getPrioritizedTasks());
        System.out.println("dcmksdkm");
        System.out.println(gson.toJson(task));

    }

    File file;

    public FileBackedTasksManager() {

        this.file = (new File("src/fileSources/SaveData.csv"));

    }




    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public Set<Task> getPrioritizedTasks() {
        return super.getPrioritizedTasks();
    }


    @Override
    public void addSubTask(Subtask subtask) {
        super.addSubTask(subtask);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void removeSubtasksInEpic(int id) {
        super.removeSubtasksInEpic(id);
        save();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubTaskById(int id) {
        Subtask subtask = super.getSubTaskById(id);
        save();
        return subtask;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        super.updateSubTask(subtask);
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
    public void removeSubTaskById(int id) {
        super.removeSubTaskById(id);
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
    public void removeAllSubTasks() {
        super.removeAllSubTasks();
        save();
    }


    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }


    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }


    String taskToString(Task task) {

        if (task.getStartTime() != null) {
            if (task.getTaskType().equals(TASK)) {
                return String.format("%d,%s,%s,%s,%s,%s,%d", task.getId(), task.getTaskType(), task.getName(),
                        task.getStatus(), task.getDescription(), task.getStartTime(), task.getDuration());
            } else if (task.getTaskType().equals(SUBTASK)) {
                Subtask subtask = (Subtask) task;
                return String.format("%d,%s,%s,%s,%s,%d,%s,%d", subtask.getId(), subtask.getTaskType(),
                        subtask.getName(), subtask.getStatus(), subtask.getDescription(), subtask.getEpicId(),
                        subtask.getStartTime(), subtask.getDuration());
            } else {
                Epic epic = (Epic) task;
                return String.format("%d,%s,%s,%s,%s,%s,%s,%d", epic.getId(), epic.getTaskType(),
                        epic.getName(), epic.getStatus(), epic.getDescription(), epic.getStartTime(),
                        epic.getEndTime(), epic.getDuration());
            }
        } else return taskToStringWithOutTime(task);
    }

    String taskToStringWithOutTime(Task task) {
        if (task.getTaskType().equals(TASK)) {
            return String.format("%d,%s,%s,%s,%s", task.getId(), task.getTaskType(), task.getName(),
                    task.getStatus(), task.getDescription());
        } else if (task.getTaskType().equals(SUBTASK)) {
            Subtask subtask = (Subtask) task;
            return String.format("%d,%s,%s,%s,%s,%d", subtask.getId(), subtask.getTaskType(),
                    subtask.getName(), subtask.getStatus(), subtask.getDescription(), subtask.getEpicId());
        } else {
            Epic epic = (Epic) task;
            return String.format("%d,%s,%s,%s,%s", epic.getId(), epic.getTaskType(),
                    epic.getName(), epic.getStatus(), epic.getDescription());
        }
    }

    static String historyToString(HistoryManager manager) {
        List<String> taskId = new ArrayList<>();
        for (Task task : manager.getHistory()) {
            taskId.add(String.valueOf(task.getId()));
        }
        return String.join(",", taskId);
    }


    public void save() {
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.append("id,type,name,status,description,epic");
            writer.newLine();
            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                writer.append(taskToString(entry.getValue()));
                writer.newLine();
            }
            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                writer.append(taskToString(entry.getValue()));
                writer.newLine();
            }
            for (Map.Entry<Integer, Subtask> entry : subTasks.entrySet()) {
                writer.append(taskToString(entry.getValue()));
                writer.newLine();
            }
            writer.newLine();
            if (this.historyManager != null) {
                writer.append(historyToString(this.historyManager));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения данных");
        }
    }

    Task taskFromString(String value) {
        String[] line = value.split(",");
        Task task = null;
        int id = Integer.parseInt(line[0]);
        TaskType type = TaskType.valueOf(line[1]);
        String name = line[2];
        Status status = Status.valueOf(line[3]);
        String description = line[4];
        int epicId;
        LocalDateTime startTime;
        LocalDateTime endTimeForEpic;
        long duration;
        if (type.equals(TASK)) {

            if (line.length == 5) {
                task = new Task(id, type, name, status, description);
            } else {
                startTime = LocalDateTime.parse(line[5]);
                duration = Long.parseLong(line[6]);
                task = new Task(id, type, name, status, description, startTime, duration);
            }

        } else if (type.equals(EPIC)) {

            if (line.length == 5) {
                task = new Epic(id, type, name, status, description);
            } else {
                startTime = LocalDateTime.parse(line[5]);
                endTimeForEpic = LocalDateTime.parse(line[6]);
                duration = Long.parseLong(line[7]);
                task = new Epic(id, type, name, status, description, startTime, endTimeForEpic, duration);
            }

        } else if (type.equals(SUBTASK)) {
            if (line.length == 6) {
                epicId = Integer.parseInt(line[5]);
                task = new Subtask(id, type, name, status, description, epicId);
            } else {
                epicId = Integer.parseInt(line[5]);
                startTime = LocalDateTime.parse(line[6]);
                duration = Long.parseLong(line[7]);
                task = new Subtask(id, type, name, status, description, startTime, duration, epicId);
            }
        }
        return task;
    }

    static List<Integer> historyFromString(String value) {
        String[] taskId = value.split(",");
        List<Integer> idTask = new ArrayList<>();
        for (String s : taskId) {
            idTask.add(Integer.parseInt(s));
        }
        return idTask;
    }


    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            br.readLine();
            while (br.ready()) {
                String line = br.readLine();
                if (!line.isEmpty()) {
                    Task task = fileBackedTasksManager.taskFromString(line);
                    if (task.getTaskType().equals(TASK)) {
                        fileBackedTasksManager.tasks.put(task.getId(), task);
                        fileBackedTasksManager.sortedTasks.add(task);
                    } else if (task.getTaskType().equals(EPIC)) {
                        fileBackedTasksManager.epics.put(task.getId(), (Epic) task);
                    } else if (task.getTaskType().equals(SUBTASK)) {
                        fileBackedTasksManager.subTasks.put(task.getId(), (Subtask) task);
                        fileBackedTasksManager.sortedTasks.add(task);
                    }
                } else {
                    line = br.readLine();
                    if (!fileBackedTasksManager.subTasks.isEmpty()) {
                        for (Subtask subtask : fileBackedTasksManager.subTasks.values()) {
                            for (Epic epic : fileBackedTasksManager.epics.values()) {
                                if (subtask.getEpicId() == epic.getId()) {
                                    epic.addEpicsSubtask(subtask);
                                }
                            }
                        }
                    }
                    if (line == null) {
                        fileBackedTasksManager.historyManager = new InMemoryHistoryManager();
                    } else {
                        List<Integer> idHistory = historyFromString(String.join(",", line));
                        for (Integer id : idHistory) {
                            if (fileBackedTasksManager.tasks.containsKey(id)) {
                                fileBackedTasksManager.historyManager.add(fileBackedTasksManager.tasks.get(id));
                            } else if (fileBackedTasksManager.epics.containsKey(id)) {
                                fileBackedTasksManager.historyManager.add(fileBackedTasksManager.epics.get(id));
                            } else if (fileBackedTasksManager.subTasks.containsKey(id)) {
                                fileBackedTasksManager.historyManager.add(fileBackedTasksManager.subTasks.get(id));
                            }
                        }
                    }
                }
            }
            br.close();
            reader.close();
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки данных");
        }
        return fileBackedTasksManager;
    }
}
