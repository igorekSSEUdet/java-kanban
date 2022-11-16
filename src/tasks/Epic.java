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

    private void updateStatus() {
        Integer progress = 0;
        Integer done = 0;
        Integer NEW = 0;
        Manager manager = new Manager();

        if (subtaskIds.isEmpty()) {
            setStatus("NEW");
        } else {
            for (Integer subtaskId : subtaskIds) {
                Subtask subtask = manager.getSubtask(subtaskId);
                if ("NEW".equals(subtask.getStatus())) {
                    NEW++;
                } else if ("IN_PROGRESS".equals(subtask.getStatus())) {
                    progress++;
                } else if ("DONE".equals(subtask.getStatus())) {
                    done++;
                }

            }
        }

        if (progress > 0) {
            setStatus("IN_PROGRESS");
        } else if (done > 0 && progress == 0) {
            setStatus("DONE");
        } else if (done == 0 && progress == 0 && NEW > 0) {
            setStatus("NEW");
        }
    }

    public void getUpdateStatus() {
        updateStatus();
    }

}