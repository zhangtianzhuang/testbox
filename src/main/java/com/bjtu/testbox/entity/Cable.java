package com.bjtu.testbox.entity;

import java.io.Serializable;

public class Cable implements Serializable {
    private int cableId;
    private String cableNumber;
    private int cableType;
    private String cableTagId;
    private String cableArea;
    private int cableBoxId;

    public Cable(String cableNumber, int cableType, String cableTagId, String cableArea, int cableBoxId) {
        this.cableNumber = cableNumber;
        this.cableType = cableType;
        this.cableTagId = cableTagId;
        this.cableArea = cableArea;
        this.cableBoxId = cableBoxId;
    }

    public Cable() {
    }

    @Override
    public String toString() {
        return "Cable{" +
                "cableId=" + cableId +
                ", cableNumber='" + cableNumber + '\'' +
                ", cableType=" + cableType +
                ", cableTagId='" + cableTagId + '\'' +
                ", cableArea='" + cableArea + '\'' +
                ", cableBoxId=" + cableBoxId +
                '}'+"\n";
    }

    public int getCableId() {
        return cableId;
    }

    public void setCableId(int cableId) {
        this.cableId = cableId;
    }

    public String getCableNumber() {
        return cableNumber;
    }

    public void setCableNumber(String cableNumber) {
        this.cableNumber = cableNumber;
    }

    public int getCableType() {
        return cableType;
    }

    public void setCableType(int cableType) {
        this.cableType = cableType;
    }

    public String getCableTagId() {
        return cableTagId;
    }

    public void setCableTagId(String cableTagId) {
        this.cableTagId = cableTagId;
    }

    public String getCableArea() {
        return cableArea;
    }

    public void setCableArea(String cableArea) {
        this.cableArea = cableArea;
    }

    public int getCableBoxId() {
        return cableBoxId;
    }

    public void setCableBoxId(int cableBoxId) {
        this.cableBoxId = cableBoxId;
    }
}
