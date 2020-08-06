package com.bjtu.testbox.service.impl;

import com.bjtu.testbox.config.api.Code;
import com.bjtu.testbox.config.api.R;
import com.bjtu.testbox.config.constant.Status;
import com.bjtu.testbox.entity.Approver;
import com.bjtu.testbox.entity.Examine;
import com.bjtu.testbox.entity.Task;
import com.bjtu.testbox.mapper.ApproverMapper;
import com.bjtu.testbox.mapper.ExamineMapper;
import com.bjtu.testbox.mapper.TaskMapper;
import com.bjtu.testbox.service.ApproverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApproverServiceImpl implements ApproverService {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ExamineMapper examineMapper;

    @Autowired
    private ApproverMapper approverMapper;

    @Override
    public List<Task> showTaskListByStatus(Integer workerId, String taskCity, Integer taskStatus,
                                           String taskPoint, Long startDate, Long endDate) {
        return taskMapper.queryTask(taskCity, workerId, taskStatus, taskPoint, startDate, endDate);
    }

    @Override
    public Task showTaskDetail(int taskId) {
        return taskMapper.queryTaskDetail(taskId);
    }

    @Override
    public Examine examineTask(Examine examine) {
        try {
            // 查找当前审批者，测试
            int approverId = 1;
            Approver approver = approverMapper.queryApproverById(approverId);
            // 审批时间
            examine.setExamineDate(System.currentTimeMillis());
            // 审批人级别
            examine.setExamineLevel(approver.getApproverLevel());
            // 审批人ID
            examine.setApproverId(approverId);
            // 判断是否已经被审批
            Examine has = examineMapper.hasExamined(approverId, examine.getTaskId());
            // 如果已经被审批
            if (has != null){
                return null;
            }
            // 添加审批记录
            examineMapper.insertExamine(examine);
            // 定义下一步任务状态，待段生产调度室审批，待领取，已拒绝
            int taskStatus = -1;
            // 如果审批结果是拒绝
            if (examine.getExamineResult() == Status.EXAMINE_REJECTED){
                taskStatus = Status.TASK_REJECTED;
            }else{
                // 当前审批者级别是动态车间
                if (examine.getExamineLevel() == Status.APPROVER_LEVEL_WORKSHOP) {
                    taskStatus = Status.TASK_WAIT_SEGMENT;
                }
                // 当前审批者级别是段生产调度室
                else/* if (examine.getExamineLevel() == Status.APPROVER_LEVEL_SEGMENT)*/{
                    taskStatus = Status.TASK_WAIT_GET;
                }
            }
            // 修改任务状态
            taskMapper.updateTaskStatus(examine.getTaskId(), taskStatus);
        } catch (Exception e) {
            return null;
        }
        return examine;
    }

    @Override
    public Approver showApproverInfo(Integer approverId) {
        return approverMapper.queryApproverById(approverId);
    }
}
