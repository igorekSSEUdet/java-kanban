package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {

    private int epicId;


    public Subtask(int id, String taskType, String name, String status,
                   String description, LocalDateTime startTime, int duration, int epicId) {
        super(id, taskType, name, status, description, startTime, duration);//для считывания
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
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