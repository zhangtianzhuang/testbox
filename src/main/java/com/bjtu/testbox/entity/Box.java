package com.bjtu.testbox.entity;

import java.io.Serializable;
import java.util.List;


public class Box implements Serializable {
    private int boxId;
    private String boxNumber;
    private int boxType;
    private String boxName;
    private String boxTagId;
    private String boxArea;
    private int boxStatus;

    // 级联,1:n
    private List<Cable> cables;
    // 长度为1.5米线缆数量
    private Integer len15 = 0;
    // 长度为2.2米线缆数量
    private Integer len22 =0;
    // 其他长度线缆数量
    private Integer lenOther=0;

    @Override
    public String toString() {
        return "Box{" +
                "boxId=" + boxId +
                ", boxNumber='" + boxNumber + '\'' +
                ", boxType=" + boxType +
                ", boxName='" + boxName + '\'' +
                ", boxTagId='" + boxTagId + '\'' +
                ", boxArea='" + boxArea + '\'' +
                ", boxStatus=" + boxStatus +
                ", \ncables=" + cables +
                ", len15=" + len15 +
                ", len22=" + len22 +
                ", lenOther=" + lenOther +
                '}'+"\n";
    }

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    public int getBoxType() {
        return boxType;
    }

    public void setBoxType(int boxType) {
        this.boxType = boxType;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public String getBoxTagId() {
        return boxTagId;
    }

    public void setBoxTagId(String boxTagId) {
        this.boxTagId = boxTagId;
    }

    public String getBoxArea() {
        return boxArea;
    }

    public void setBoxArea(String boxArea) {
        this.boxArea = boxArea;
    }

    public int getBoxStatus() {
        return boxStatus;
    }

    public void setBoxStatus(int boxStatus) {
        this.boxStatus = boxStatus;
    }

    public List<Cable> getCables() {
        return cables;
    }

    public void setCables(List<Cable> cables) {
        this.cables = cables;
    }

    public Integer getLen15() {
        return len15;
    }

    public void setLen15(Integer len15) {
        this.len15 = len15;
    }

    public Integer getLen22() {
        return len22;
    }

    public void setLen22(Integer len22) {
        this.len22 = len22;
    }

    public Integer getLenOther() {
        return lenOther;
    }

    public void setLenOther(Integer lenOther) {
        this.lenOther = lenOther;
    }
}
