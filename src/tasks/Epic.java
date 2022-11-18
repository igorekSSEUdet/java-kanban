package tasks;

import manager.Manager;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds = new ArrayList<>();


    public Epic(String name, String description) {// for create
        super(name, description);
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


}