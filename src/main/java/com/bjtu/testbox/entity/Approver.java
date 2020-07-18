package com.bjtu.testbox.entity;

public class Approver {
    private int approverId;
    private String approverNumber;
    private String approverName;
    private int approverLevel;
    private String approverPhone;

    @Override
    public String toString() {
        return "Approver{" +
                "approverId=" + approverId +
                ", approverNumber='" + approverNumber + '\'' +
                ", approverName='" + approverName + '\'' +
                ", approverLevel=" + approverLevel +
                ", approverPhone='" + approverPhone + '\'' +
                '}'+"\n";
    }

    public int getApproverId() {
        return approverId;
    }

    public void setApproverId(int approverId) {
        this.approverId = approverId;
    }

    public String getApproverNumber() {
        return approverNumber;
    }

    public void setApproverNumber(String approverNumber) {
        this.approverNumber = approverNumber;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public int getApproverLevel() {
        return approverLevel;
    }

    public void setApproverLevel(int approverLevel) {
        this.approverLevel = approverLevel;
    }

    public String getApproverPhone() {
        return approverPhone;
    }

    public void setApproverPhone(String approverPhone) {
        this.approverPhone = approverPhone;
    }
}
