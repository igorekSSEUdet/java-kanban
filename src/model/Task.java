package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected Status status;
    protected int id;
    protected TaskType taskType;

    protected LocalDateTime startTime;

    protected int duration = 0;


    public Task(int id, String taskType, String name, String status, String description, LocalDateTime startTime
            , int duration) {//для считывания
        this.name = name;
        this.description = description;
        this.status = Status.valueOf(status);
        this.id = id;
        this.taskType = TaskType.valueOf(taskType);
        this.startTime = startTime;
        this.duration = duration;
    }


    public Task(String name, String description, LocalDateTime startTime, int duration) {//для создания
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
    }


    public Task(String name, String description) {//для эпика
        this.name = name;
        this.description = description;
    }


    public LocalDateTime getStartTime() {
        if (startTime != null) {
            return startTime;
        } else return null;

    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        LocalDateTime endTime = startTime.plusMinutes(duration);
        return endTime;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, status, id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                ", taskType=" + taskType +
                '}';
    }
}