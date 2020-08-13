package com.bjtu.testbox.entity;

import java.io.Serializable;

public class Admin implements Serializable {
    private Integer adminId;
    private String adminNumber;
    private String adminName;
    private Integer adminLevel;
    private String adminPhone;

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", adminNumber='" + adminNumber + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminLevel=" + adminLevel +
                ", adminPhone='" + adminPhone + '\'' +
                '}';
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminNumber() {
        return adminNumber;
    }

    public void setAdminNumber(String adminNumber) {
        this.adminNumber = adminNumber;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }
}
