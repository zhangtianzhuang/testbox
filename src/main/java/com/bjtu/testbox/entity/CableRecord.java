package com.bjtu.testbox.entity;

import java.io.Serializable;

/**
 * @program: testbox
 * @description: 手机端线缆使用记录
 * @author: Mr.Bruin
 * @create: 2020-08-22 23:34
 **/
public class CableRecord implements Serializable {
    private Integer cRecordId;
    private String cRecordBox;
    private String cRecordNumber;
    private Integer cRecordTask;
    private Long cRecordStartDate;
    private Long cRecordEndDate;

    public Integer getcRecordId() {
        return cRecordId;
    }

    public void setcRecordId(Integer cRecordId) {
        this.cRecordId = cRecordId;
    }

    public String getcRecordBox() {
        return cRecordBox;
    }

    public void setcRecordBox(String cRecordBox) {
        this.cRecordBox = cRecordBox;
    }

    public String getcRecordNumber() {
        return cRecordNumber;
    }

    public void setcRecordNumber(String cRecordNumber) {
        this.cRecordNumber = cRecordNumber;
    }

    public Integer getcRecordTask() {
        return cRecordTask;
    }

    public void setcRecordTask(Integer cRecordTask) {
        this.cRecordTask = cRecordTask;
    }

    public Long getcRecordStartDate() {
        return cRecordStartDate;
    }

    public void setcRecordStartDate(Long cRecordStartDate) {
        this.cRecordStartDate = cRecordStartDate;
    }

    public Long getcRecordEndDate() {
        return cRecordEndDate;
    }

    public void setcRecordEndDate(Long cRecordEndDate) {
        this.cRecordEndDate = cRecordEndDate;
    }

    @Override
    public String toString() {
        return "CableRecord{" +
                "cRecordId=" + cRecordId +
                ", cRecordBox='" + cRecordBox + '\'' +
                ", cRecordNumber='" + cRecordNumber + '\'' +
                ", cRecordTask=" + cRecordTask +
                ", cRecordStartDate=" + cRecordStartDate +
                ", cRecordEndDate=" + cRecordEndDate +
                '}';
    }
}
