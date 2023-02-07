package model;

import manager.Status;

import java.time.LocalDateTime;

public class Subtask extends Task {

    private int epicId;


    public Subtask(int id, String taskType, String name, String status,
                   String description, LocalDateTime startTime, LocalDateTime endTime, int duration, int epicId) {
        super(id, taskType, name, status, description, startTime, endTime, duration);//дял считывания
        this.epicId = epicId;
    }

    public Subtask(String name, String description, LocalDateTime startTime, int duration, int epicId) {
        super(name, description, startTime, duration);//для создания
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                ", taskType=" + taskType +
                '}';
    }
}