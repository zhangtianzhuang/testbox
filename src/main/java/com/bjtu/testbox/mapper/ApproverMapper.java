package com.bjtu.testbox.mapper;

import com.bjtu.testbox.entity.Approver;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ApproverMapper {

    // ++++++++++++++++++++++++++++ select

    @Select("select * from approver where approver_id = #{id}")
    Approver queryApproverById(int id);

    // ++++++++++++++++++++++++++++ insert

    @Insert("insert into approver (approver_number, approver_name, approver_level, approver_phone)" +
            " values (#{approverNumber}, #{approverName}, #{approverLevel}, #{approverPhone})")
    void insertApprover(Approver approver);

    // ++++++++++++++++++++++++++++ update

    @Update("update approver set approver_phone = #{approverPhone} where approver_id = #{approverId}")
    void updateApproverPhone(@Param("approverPhone") String approverPhone, @Param("approverId") int approverId);

    // ++++++++++++++++++++++++++++ delete
    @Delete("delete from approver where approver_id = #{approverId}")
    void deleteApprover(Integer approverId);


}
