package com.bjtu.testbox.config.constant;

public class Status {

    //////   任务状态   ///////
    // （1）	待车间审核
    public static final int TASK_WAIT_WORKSHOP = 1;
    // （2）	待段生产调度审核
    public static final int TASK_WAIT_SEGMENT = 2;
    // （3）	待领取
    public static final int TASK_WAIT_GET = 3;
    // （4）	待归还
    public static final int TASK_WAIT_RETURN = 4;
    // （5）	已完成
    public static final int TASK_FINISHED = 5;
    // （6）	被拒绝
    public static final int TASK_REJECTED = 6;
    //////   任务状态   ///////

    //////   审批者级别   ///////
    // （7）	动态车间
    public static final int APPROVER_LEVEL_WORKSHOP = 1;
    // （8）	段生产调度室
    public static final int APPROVER_LEVEL_SEGMENT = 2;
    //////   审批者级别   ///////


    //////   试验箱类型   ///////
    // （1）	直流岔道型
    public static final int BOX_TYPE_DC_CROSSROAD = 0;
    // （2）	交流岔道型
    public static final int BOX_TYPE_AC_CROSSROAD = 1;
    // （3）	轨道电路型
    public static final int BOX_TYPE_ELECTRICITY = 2;
    //////   试验箱类型   ///////


    //////   试验箱状态   ///////
    // （1）	在库中 0
    public static final int BOX_STATUS_IN_REPOSITORY = 0;
    // （2）	故障 1
    public static final int BOX_STATUS_BUG = 1;
    // （3）	出库 2
    public static final int BOX_STATUS_OUT_REPOSITORY = 2;
    // （4）	丢失 3
    public static final int BOX_STATUS_LOST = 3;
    //////   试验箱状态   ///////


    //////   审批结果   ///////
    // 通过 1
    public static final int EXAMINE_APPROVED = 1;
    // 拒绝 2
    public static final int EXAMINE_REJECTED = 2;
    //////   审批结果   ///////

    //////   用户类型标识   ///////
    // 普通工人 1
    public static final int USER_TYPE_WORKER = 1;
    // 动态车间审批者 2
    public static final int USER_TYPE_WORKSHOP_APPROVER = 2;
    // 段生产审批者 3
    public static final int USER_TYPE_SEGMENT_APPROVER = 3;
    //////   用户类型标识   ///////
}