package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {

    private List<Subtask> subtasks = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(int id, TaskType type, String name, Status status, String description,
                LocalDateTime startTime, LocalDateTime endTime,Long duration) {//for fileBackedTaskManager load
        super(id, type, name, status, description, startTime, duration);
        this.endTime = endTime;
    }

    public Epic(int id, TaskType type, String name, Status status, String description) {
        super(id, type, name, status, description);
    }

    public void calculateEpicTime(Subtask subtask) {

        if (subtask.startTime == null) return;
        if (this.endTime == null && this.startTime == null) {
            this.startTime = subtask.getStartTime();
            this.endTime = subtask.getEndTime();
        } else if (subtask.getStartTime().isBefore(startTime)) {
            startTime = subtask.getStartTime();
        } else if (subtask.getEndTime().isAfter(endTime)) {
            endTime = subtask.getEndTime();
        }
    }

    public void plusDuration(Subtask subtask) {
        this.duration += subtask.getDuration();
    }

    @Override
    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void minusDuration(Subtask subtask) {
        this.duration -= subtask.getDuration();
    }

    public void clearSubtasksList() {
        subtasks.clear();
    }


    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void addEpicsSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public void removeEpicsSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasks, epic.subtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasks);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasks=" + subtasks +
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
