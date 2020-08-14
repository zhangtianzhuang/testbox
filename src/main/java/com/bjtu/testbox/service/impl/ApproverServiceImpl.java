package com.bjtu.testbox.service.impl;

import com.bjtu.testbox.config.constant.Status;
import com.bjtu.testbox.config.shiro.AppSecurityUtils;
import com.bjtu.testbox.entity.Approver;
import com.bjtu.testbox.entity.Examine;
import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.mapper.ApproverMapper;
import com.bjtu.testbox.mapper.ExamineMapper;
import com.bjtu.testbox.mapper.TaskMapper;
import com.bjtu.testbox.service.ApproverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApproverServiceImpl implements ApproverService {

    private static final Logger logger = LoggerFactory.getLogger(ApproverServiceImpl.class);
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private ExamineMapper examineMapper;
    @Autowired
    private ApproverMapper approverMapper;
    @Autowired
    private AppSecurityUtils appSecurity;

    @Override
    public List<Task> showTaskListByStatus(Integer workerId, String taskCity, Integer taskStatus,
                                           String taskPoint, Long startDate, Long endDate) {
        return taskMapper.queryTask(taskCity, workerId, taskStatus, taskPoint, startDate, endDate);
    }

    @Override
    public Task showTaskDetail(int taskId) {
        return taskMapper.queryTaskDetail(taskId);
    }

    /**
     * @param examine
     * @return -1 已经被审批
     * -2 审批者与任务不符
     * -3 审批发生异常
     * -4 任务当前不符合被审批状态
     * 任务ID 审批通过
     */
    @Override
    public Integer examineTask(Examine examine) {
        try {
            // 查找当前审批者ID
            int approverId = appSecurity.getBindId();
            logger.info("examineTask" + ": approverId:" + approverId);
            Approver approver = approverMapper.queryApproverById(approverId);
            examine.setExamineDate(System.currentTimeMillis());// 审批时间
            examine.setExamineLevel(approver.getApproverLevel());// 审批人级别
            examine.setApproverId(approverId);// 审批人ID
            // 判断是否已经被审批
            Examine has = examineMapper.hasExamined(approverId, examine.getTaskId());
            // 如果已经被审批
            if (has != null) {
                logger.error("examineTask" + " >>> 任务被该审批者审批了");
                return -1;
            }
            // 判断是否可以审批，判断当前任务的状态
            Integer status = taskMapper.statusById(examine.getTaskId());
            Integer level = approver.getApproverLevel();
            // 下面分两种情况
            // 1.如果任务状态处于待动态车间审核，但审批人不是动态车间级别
            // 2.如果任务状态处于待生产调度室审核，但审核人不是动态调度室级别
            // 将进行异常处理
            if ((status == Status.TASK_WAIT_WORKSHOP && level != Status.APPROVER_LEVEL_WORKSHOP) ||
                    (status == Status.TASK_WAIT_SEGMENT && level != Status.APPROVER_LEVEL_SEGMENT)) {
                logger.error("examineTask" + ": 审批人级别和任务状态不匹配");
                return -2;
            }
            // 判断是否处于被审批状态
            if (status != Status.TASK_WAIT_SEGMENT && status != Status.TASK_WAIT_WORKSHOP) {
                return -4;
            }
            logger.info("examineTask" + ": >>>>>> 添加审批记录");
            // 添加审批记录
            examineMapper.insertExamine(examine);
            // 定义下一步任务状态，待段生产调度室审批，待领取，已拒绝
            int taskStatus = -1;
            // 如果审批结果是拒绝
            if (examine.getExamineResult() == Status.EXAMINE_REJECTED) {
                logger.info("examineTask" + " >>> 审批结果拒绝");
                taskStatus = Status.TASK_REJECTED;
            } else {
                // 当前审批者级别是动态车间
                if (examine.getExamineLevel() == Status.APPROVER_LEVEL_WORKSHOP) {
                    logger.info("examineTask" + " >>> 当前是动态车间，审批通过");
                    taskStatus = Status.TASK_WAIT_SEGMENT;
                }
                // 当前审批者级别是段生产调度室
                else/* if (examine.getExamineLevel() == Status.APPROVER_LEVEL_SEGMENT)*/ {
                    logger.info("examineTask" + " >>> 当前是段生产调度室，审批通过");
                    taskStatus = Status.TASK_WAIT_GET;
                }
            }
            // 修改任务状态
            taskMapper.updateTaskStatus(examine.getTaskId(), taskStatus);
            logger.info("examineTask" + " >>> 审批已经完成!");
        } catch (Exception e) {
            logger.error("examineTask" + " >>> 发生异常");
            return -3;
        }
        return examine.getTaskId();
    }

    @Override
    public Approver showApproverInfo(Integer approverId) {
        return approverMapper.queryApproverById(approverId);
    }

    @Override
    public List<Task> showHistoryTask(Integer approverId, Integer examineResult) {
        return taskMapper.queryTaskByApprover(approverId, examineResult);
    }
}
