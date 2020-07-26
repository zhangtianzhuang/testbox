package com.bjtu.testbox.entity;

public class Worker {
    private int workerId;
    private String workerName;
    private String workerNumber;
    private String workerArea;
    private String workerGroup;
    private String workerCity;
    private String workerPhone;

    public Worker(){}

    public Worker(String workerName, String workerNumber, String workerArea, String workerGroup, String workerCity, String workerPhone) {
        this.workerName = workerName;
        this.workerNumber = workerNumber;
        this.workerArea = workerArea;
        this.workerGroup = workerGroup;
        this.workerCity = workerCity;
        this.workerPhone = workerPhone;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "workerId=" + workerId +
                ", workerName='" + workerName + '\'' +
                ", workerNumber='" + workerNumber + '\'' +
                ", workerArea='" + workerArea + '\'' +
                ", workerGroup='" + workerGroup + '\'' +
                ", workerCity='" + workerCity + '\'' +
                ", workerPhone='" + workerPhone + '\'' +
                '}'+"\n";
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerNumber() {
        return workerNumber;
    }

    public void setWorkerNumber(String workerNumber) {
        this.workerNumber = workerNumber;
    }

    public String getWorkerArea() {
        return workerArea;
    }

    public void setWorkerArea(String workerArea) {
        this.workerArea = workerArea;
    }

    public String getWorkerGroup() {
        return workerGroup;
    }

    public void setWorkerGroup(String workerGroup) {
        this.workerGroup = workerGroup;
    }

    public String getWorkerCity() {
        return workerCity;
    }

    public void setWorkerCity(String workerCity) {
        this.workerCity = workerCity;
    }

    public String getWorkerPhone() {
        return workerPhone;
    }

    public void setWorkerPhone(String workerPhone) {
        this.workerPhone = workerPhone;
    }
}
