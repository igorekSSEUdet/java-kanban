package model;

import manager.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks = new ArrayList<>();

    LocalDateTime endTime;


    public Epic(String name, String description) {
        super(name, description);//для создания
    }

    public Epic(int id, String taskType, String name, String status, String description,
                LocalDateTime startTime, LocalDateTime endTime, int duration) {
        super(id, taskType, name, status, description, startTime, endTime, duration);//для считывания
        this.endTime = getEndTime();
    }

    public void countTime(Subtask subtask) {
      if (this.endTime == null && this.startTime == null) {
            this.endTime = subtask.getEndTime();
            this.startTime = subtask.getStartTime();
      } else if (subtask.getEndTime().isAfter(endTime)) {
          endTime = subtask.getEndTime();
      } else if (subtask.getStartTime().isBefore(startTime)) {
          startTime = subtask.getStartTime();
      }
    }

    public void emptyTime() {
        this.startTime = null;
        this.endTime = null;
    }






    public void plusDuration(int duration) {
        this.duration += duration;

    }

    public void minusDuration(int duration) {
        this.duration -= duration;

    }

    public void emptyDuration() {
        this.duration = 0;

    }




    public ArrayList<Subtask> getSubtaskIds() {
        return subtasks;
    }

    public void setSubtask(Subtask subtask) {
        this.subtasks.add(subtask);
    }

    public ArrayList<Integer> getSubtasksIds() {
        ArrayList<Integer> subtasksIds = new ArrayList<>(subtasks.size());
        for (Subtask subtask : subtasks) {
            subtasksIds.add(subtask.getId());
        }
        return subtasksIds;
    }

    public void removeSubtasks() {
        this.subtasks.clear();
    }

    public void removeSubtask(Subtask subtask) {
        this.subtasks.remove(subtask);
    }

    public int lengthList() {
        return this.subtasks.size();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasks=" + subtasks +
                ", endTime=" + endTime +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                ", taskType=" + taskType +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }
}