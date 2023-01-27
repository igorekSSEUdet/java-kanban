package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {

   private Integer epicId;


    public Subtask(String name, String description, Status status, LocalDateTime startTime, Long duration, Integer epicId) {
        super(name, description, status, startTime, duration);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, Status status, Integer epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public Subtask(int id, TaskType type, String name, Status status, String description, Integer epicId) {
        super(id, type, name, status, description);
        this.epicId = epicId;
    }

    public Subtask(int id, TaskType type, String name, Status status, String description,
                   LocalDateTime startTime, Long duration, Integer epicId) {
        super(id, type, name, status, description, startTime, duration);
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return Objects.equals(epicId, subtask.epicId);
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
                ", taskType='" + taskType + '\'' +
                ", status='" + status + '\'' +
                ", id=" + id +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }
}
