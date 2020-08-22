package com.bjtu.testbox.mapper;

import com.bjtu.testbox.entity.CableRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CableRecordMapper {
    @Insert("insert into c_record (c_record_box, c_record_number, c_record_task, " +
            "c_record_start_date, c_record_end_date) values (#{cRecordBox}, #{cRecordNumber}, " +
            "#{cRecordTask}, #{cRecordStartDate}, #{cRecordEndDate})")
    Integer insertCableRecord(CableRecord cableRecord);

    @Select("select * from c_record where c_record_task = #{taskId}")
    List<CableRecord> queryCableRecordList(@Param("taskId") Integer taskId);
}
