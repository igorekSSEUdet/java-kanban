package tasks;

import java.util.HashMap;
import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Subtask> subtasks;

    public Epic(String name, String description, String status, int number, ArrayList<Subtask> subtasks) {
        super(name, description, status, number);
        this.subtasks = subtasks;
    }

    public Epic(String name, String description, String status) {
        super(name, description, status);
        this.subtasks = subtasks;
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public void updateStatus() {//обновить статус

        if (subtasks.isEmpty()) {
            setStatus("NEW");
        }
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() == "IN_PROGRESS") {
                setStatus("IN_PROGRESS");
            } else {
                setStatus("DONE");
            }
        }
    }
}