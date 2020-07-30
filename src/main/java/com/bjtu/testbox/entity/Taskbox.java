package com.bjtu.testbox.entity;

import java.io.Serializable;

public class Taskbox implements Serializable {
    private int taskBoxId;
    private int taskId;
    private int boxId;

    @Override
    public String toString() {
        return "Taskbox{" +
                "taskBoxId=" + taskBoxId +
                ", taskId=" + taskId +
                ", boxId=" + boxId +
                '}'+"\n";
    }

    public int getTaskBoxId() {
        return taskBoxId;
    }

    public void setTaskBoxId(int taskBoxId) {
        this.taskBoxId = taskBoxId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }
}
