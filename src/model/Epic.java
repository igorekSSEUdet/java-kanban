package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds = new ArrayList<>();


    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(int id, String taskType, String name, String status, String description) {
        super(id, taskType, name, status, description);

    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void setSubtaskIds(Integer subtaskId) {
        this.subtaskIds.add(subtaskId);
    }

    public void removeSubtasks() {
        this.subtaskIds.clear();
    }

    public void removeIdSubtasks(Integer id) {
        this.subtaskIds.remove(id);
    }

    public int lengthList() {
        return this.subtaskIds.size();
    }

    public int getIdOfSubtask(int id) {
        return this.subtaskIds.get(id);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtaskIds=" + subtaskIds +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                ", taskType=" + taskType +
                '}';
    }
}