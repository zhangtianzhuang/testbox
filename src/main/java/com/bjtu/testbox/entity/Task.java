package com.bjtu.testbox.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel("任务实体")

public class Task implements Serializable {
    private int taskId;
    @ApiModelProperty("任务编号,由系统自动生成")
    private String taskNumber;
    private String taskName;
    private int taskWorkerId;
    private String taskWorkerName;
    private String taskPoint;
    private long taskDate;
    private long borrowDate;
    private long returnDate;
    private int taskStatus;
    private String taskDesc="";
    private String taskCity;
    private String taskArea;

    // 一个任务可申请多个试验箱
    private List<Box> boxes;

    // 审批信息，查询任务详情时用到
    private List<Examine> examines;

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskNumber='" + taskNumber + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskWorkerId=" + taskWorkerId +
                ", taskWorkerName='" + taskWorkerName + '\'' +
                ", taskPoint='" + taskPoint + '\'' +
                ", taskDate=" + taskDate +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", taskStatus=" + taskStatus +
                ", taskDesc='" + taskDesc + '\'' +
                ", taskCity='" + taskCity + '\'' +
                ", taskArea='" + taskArea + '\'' +
                ", \nboxes=" + boxes +
                ", \nexamines=" + examines +
                "}\n";
    }

    public List<Examine> getExamines() {
        return examines;
    }

    public void setExamines(List<Examine> examines) {
        this.examines = examines;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskWorkerId() {
        return taskWorkerId;
    }

    public void setTaskWorkerId(int taskWorkerId) {
        this.taskWorkerId = taskWorkerId;
    }

    public String getTaskWorkerName() {
        return taskWorkerName;
    }

    public void setTaskWorkerName(String taskWorkerName) {
        this.taskWorkerName = taskWorkerName;
    }

    public String getTaskPoint() {
        return taskPoint;
    }

    public void setTaskPoint(String taskPoint) {
        this.taskPoint = taskPoint;
    }

    public long getTaskDate() {
        return taskDate;
    }

    public String getTaskDate(boolean timestamp){
        if(timestamp)
            return String.valueOf(taskDate);
        else{
            Date date = new Date(taskDate);
            return String.valueOf(date);
        }
    }

    public void setTaskDate(long taskDate) {
        this.taskDate = taskDate;
    }

    public long getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(long borrowDate) {
        this.borrowDate = borrowDate;
    }

    public long getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(long returnDate) {
        this.returnDate = returnDate;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getTaskCity() {
        return taskCity;
    }

    public void setTaskCity(String taskCity) {
        this.taskCity = taskCity;
    }

    public String getTaskArea() {
        return taskArea;
    }

    public void setTaskArea(String taskArea) {
        this.taskArea = taskArea;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }
}
