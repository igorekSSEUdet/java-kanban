package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {

    protected String name;
    protected String description;
    protected TaskType taskType;
    protected Status status;
    protected Integer id;
    protected LocalDateTime startTime;
    Long duration = 0L;


    public Task(String name, String description, Status status, LocalDateTime startTime, Long duration) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(String name, String description, Status status) {//for create without time
        this.name = name;
        this.description = description;
        this.status = status;

    }

    public Task(int id,TaskType type,String name,Status status,String description) {//for fileBackedTaskManager load
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;
        this.taskType = type;

    }

    public Task(int id,TaskType type,String name,Status status,String description,LocalDateTime startTime,Long duration) {
        this.name = name;
        this.description = description;//for fileBackedTaskManager load
        this.status = status;
        this.id = id;
        this.taskType = type;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(String name, String description) {// for epic
        this.name = name;
        this.description = description;

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

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description) && taskType == task.taskType && status == task.status && Objects.equals(id, task.id) && Objects.equals(startTime, task.startTime) && Objects.equals(duration, task.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, taskType, status, id, startTime, duration);
    }

    public LocalDateTime getEndTime() {
        if (duration != null) return startTime.plusMinutes(duration);
        else return startTime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskType='" + taskType + '\'' +
                ", status='" + status + '\'' +
                ", id=" + id +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }
}
