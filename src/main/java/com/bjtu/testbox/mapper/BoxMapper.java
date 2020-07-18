package com.bjtu.testbox.mapper;

import com.bjtu.testbox.entity.Box;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxMapper {

    // +++++++++++++++++ select

    /**
     * query a Box and all of cables in this box
     * @param id  test-box's ID
     * @return
     */
    Box selectBoxAndCable(int id);

    /**
     * query if the {boxNumber} exists in the database.
     * @param boxNumber
     * @return
     */
    @Select("select box_number from box where box_number = #{boxNumber}")
    String selectBoxNumber(String boxNumber);

    @Select("select * from box where box_id = #{boxId}")
    Box selectBasicInfo(int boxId);

    @Select("select box_id from box where box_id = #{boxId}")
    Integer selectBoxId(int boxId);

    // +++++++++++++++++ insert

    /**
     * @param box
     */
    void insertBox(Box box);


    // +++++++++++++++++ update

    /**
     *  update box's status
     * @param boxId
     * @param boxStatus
     */
    void updateBoxStatus(@Param("boxId") int boxId, @Param("boxStatus") int boxStatus);

    /**
     *
     * @param boxArea
     * @param boxTagId
     */
    void updateBoxBasicInfo(@Param("boxArea")String boxArea, @Param("boxTagId")String boxTagId,
                            @Param("boxId")int boxId);
}

