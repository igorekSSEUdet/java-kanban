package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Subtask> subtasks;

    public Epic(String name, String description, String status, int number, ArrayList<Subtask> subtasks) {
        super(name, description, status);
        this.subtasks = subtasks;
    }

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public void updateStatus() {//обновить статус
        Integer progress = 0;
        Integer done = 0;
        Integer NEW = 0;

        if (subtasks.isEmpty()) {
            setStatus("NEW");
        }
        for (Subtask subtask : subtasks) {
            if ("NEW".equals(subtask.getStatus())) {
                NEW++;
            } else if ("IN_PROGRESS".equals(subtask.getStatus())) {
                progress++;
                //setStatus("IN_PROGRESS");
            } else {
                done++;
                //setStatus("DONE");
            }
        }
        if (progress > NEW) {
            setStatus("IN_PROGRESS");
        } else if (NEW > progress) {
            setStatus("NEW");
        } else if (progress == 0 || NEW == 0 || done != 0) {
            setStatus("DONE");
        }else return;
    }


}