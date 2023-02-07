package manager;

import exceptions.ManagerSaveException;
import historyManager.HistoryManager;
import model.Epic;
import model.Subtask;
import model.Task;


import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static manager.Status.NEW;
import static model.TaskType.*;


class FileBackedTasksManager extends InMemoryTaskManager {

    public static void main(String[] args) {

        File file1 = new File("src/sources/SaveData.csv");
        FileBackedTasksManager manager = loadFromFile(file1);
        FileBackedTasksManager manager1 = loadFromFile(file1);
//        Task task = new Task("name1", "desc");
//        Task task1 = new Task("name2", "desc");
//        Task task2 = new Task("name3", "desc");
        LocalDateTime dateTime = LocalDateTime.of(2023, 1,
                20, 19, 15);
        LocalDateTime dateTime1 = LocalDateTime.of(2023, 1,
                20, 20, 15);
        LocalDateTime dateTime2 = LocalDateTime.of(2023, 1,
                20, 21, 15);


        Epic epic = new Epic("name4", "desc");

        Subtask subtask1 = new Subtask("nameSub", "descSub", dateTime, 60, epic.getId());

        Subtask subtask2 = new Subtask("nameSub", "descSub", dateTime1, 60, epic.getId());
        Subtask subtask3 = new Subtask("nameSub", "descSub", dateTime2, 60, epic.getId());


        manager.addEpic(epic);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addSubtask(subtask3);
//        manager.addTask(task);
//        manager.addTask(task1);
//        manager.addTask(task2);
//
//        manager.getTaskById(task.getId());
//        manager.getTaskById(task1.getId());
//        manager.getTaskById(task2.getId());
        manager.getSubtaskById(subtask1.getId());
        manager.getSubtaskById(subtask2.getId());
        manager.getSubtaskById(subtask3.getId());
        manager.getEpicById(epic.getId());

//        manager.removeAllTasks();

        System.out.println(manager.epics);

        System.out.println(manager1.epics);

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
    public Task getTaskById(int id) {
        super.getTaskById(id);
        save();
        return null;
    }


    @Override
    public Epic getEpicById(int id) {
        super.getEpicById(id);
        save();
        return null;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        super.getSubtaskById(id);
        save();
        return null;
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
            return String.format("%d,%s,%s,%s,%s,%s,%s,%d", task.getId(), task.getTaskType(), task.getName(),
                    task.getStatus(), task.getDescription(), task.getStartTime(), task.getEndTime(), task.getDuration());
        } else if (task.getTaskType().equals(SUBTASK)) {
            Subtask subtask = (Subtask) task;
            return String.format("%d,%s,%s,%s,%s,%d,%s,%s,%d", subtask.getId(), subtask.getTaskType(),
                    subtask.getName(), subtask.getStatus(), subtask.getDescription(), subtask.getEpicId(),
                    subtask.getStartTime(), subtask.getEndTime(), subtask.getDuration());
        } else {
            Epic epic = (Epic) task;
            return String.format("%d,%s,%s,%s,%s,%s,%s,%d", epic.getId(), epic.getTaskType(),
                    epic.getName(), epic.getStatus(), epic.getDescription(), epic.getStartTime(), epic.getEndTime(),
                    epic.getDuration());
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
            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                writer.append(toString(entry.getValue()));
                writer.newLine();
            }
            for (Map.Entry<Integer, Subtask> entry : subtasks.entrySet()) {
                writer.append(toString(entry.getValue()));
                writer.newLine();
            }
            writer.newLine();
            writer.append(historyToString(this.inMemoryHistoryManager));
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения данных");
        }
    }

    Task taskFromString(String value) {
        String[] line = value.split(",");
        Task task = null;
        int id = Integer.parseInt(line[0]);
        String type = line[1];
        String name = line[2];
        String status = line[3];
        String description = line[4];
        int epicId;
        LocalDateTime startTime;
        LocalDateTime endTime;
        int duration;
        if (type.equals(TASK.toString())) {

            if (line[5].equals("null")) {
                startTime = null;
            } else {
                startTime = LocalDateTime.parse(line[5]);
            }
             if (line[5].equals("null")) {
                endTime = null;
            } else {
                endTime =  LocalDateTime.parse(line[6]);
            }
            duration = Integer.parseInt(line[7]);
            task = new Task(id, type, name, status, description,startTime,endTime,duration);
        } else if (type.equals(EPIC.toString())) {
//            if (line[5].equals("null")) {
//                startTime = null;
//            } else {
//                startTime = LocalDateTime.parse(line[5]);
//            }
//
//            if (line[6].isEmpty() || line[5] == null || line[5].equals("null")) {
//                endTime = null;
//            } else {
//                endTime =  LocalDateTime.parse(line[6]);
//            }
//            duration = Integer.parseInt(line[7]);
            task = new Epic(name,description);
        } else if (type.equals(SUBTASK.toString())) {
            epicId = Integer.parseInt(line[5]);
            startTime = LocalDateTime.parse(line[6]);
            endTime =  LocalDateTime.parse(line[7]);
            duration = Integer.parseInt(line[8]);
            task = new Subtask(id, type, name, status, description,startTime,endTime,duration,epicId);
        }
        return task;
    }


    static FileBackedTasksManager loadFromFile(File file) {
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
                    } else if (task.getTaskType().equals(EPIC)) {
                        fileBackedTasksManager.epics.put(task.getId(), (Epic) task);
                    } else if (task.getTaskType().equals(SUBTASK)) {
                        fileBackedTasksManager.subtasks.put(task.getId(), (Subtask) task);
                    }
                } else {
                    line = br.readLine();
                    if (!fileBackedTasksManager.subtasks.isEmpty()) {
                        for (Subtask subtask : fileBackedTasksManager.subtasks.values()) {
                            for (Epic epic : fileBackedTasksManager.epics.values()) {
                                if (subtask.getEpicId() == epic.getId()) {
                                    epic.setSubtask(subtask);
                                }
                            }

                        }

                    }
                    if (line.isEmpty()) {
                        fileBackedTasksManager.inMemoryHistoryManager = null;
                    } else {
                        List<Integer> idHistory = historyFromString(String.join(",", line));
                        for (Integer id : idHistory) {
                            if (fileBackedTasksManager.tasks.containsKey(id)) {
                                fileBackedTasksManager.inMemoryHistoryManager.add(fileBackedTasksManager.tasks.get(id));
                            } else if (fileBackedTasksManager.epics.containsKey(id)) {
                                fileBackedTasksManager.inMemoryHistoryManager.add(fileBackedTasksManager.epics.get(id));
                            } else if (fileBackedTasksManager.subtasks.containsKey(id)) {
                                fileBackedTasksManager.inMemoryHistoryManager.add(fileBackedTasksManager.subtasks.get(id));
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