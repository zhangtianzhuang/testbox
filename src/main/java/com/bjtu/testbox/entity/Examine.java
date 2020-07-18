package com.bjtu.testbox.entity;

public class Examine {
    private int examineId;
    private int approverId;
    private int taskId;
    private int examineLevel;
    private int examineResult;
    private long examineDate;
    private String examineReason;

    @Override
    public String toString() {
        return "Examine{" +
                "examineId=" + examineId +
                ", approverId=" + approverId +
                ", taskId=" + taskId +
                ", examineLevel=" + examineLevel +
                ", examineResult=" + examineResult +
                ", examineDate=" + examineDate +
                ", examineReason='" + examineReason + '\'' +
                '}'+"\n";
    }

    public int getExamineId() {
        return examineId;
    }

    public void setExamineId(int examineId) {
        this.examineId = examineId;
    }

    public int getApproverId() {
        return approverId;
    }

    public void setApproverId(int approverId) {
        this.approverId = approverId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getExamineLevel() {
        return examineLevel;
    }

    public void setExamineLevel(int examineLevel) {
        this.examineLevel = examineLevel;
    }

    public int getExamineResult() {
        return examineResult;
    }

    public void setExamineResult(int examineResult) {
        this.examineResult = examineResult;
    }

    public long getExamineDate() {
        return examineDate;
    }

    public void setExamineDate(long examineDate) {
        this.examineDate = examineDate;
    }

    public String getExamineReason() {
        return examineReason;
    }

    public void setExamineReason(String examineReason) {
        this.examineReason = examineReason;
    }
}
