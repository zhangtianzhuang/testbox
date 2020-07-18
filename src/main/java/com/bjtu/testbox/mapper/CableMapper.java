package com.bjtu.testbox.mapper;

import com.bjtu.testbox.entity.Cable;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CableMapper {

    // select

    /**
     * query the list of the cables by box's id
     * @param id  box's id
     * @return  the list of cables
     */
    List<Cable> findCableByBoxId(int id);


    /**
     * query the cable number if exist in the box
     * @param boxId
     * @param cableNumber
     * @return
     */
    @Select("select cable_number from cable where cable_box_id = #{boxId} and cable_number = #{cableNumber}")
    String findCableInBox(@Param("boxId") int boxId, @Param("cableNumber") String cableNumber);


    @Select("select * from cable where cable_id = #{cableId}")
    Cable findCable(int cableId);

    // insert

    /**
     * insert a cable
     * @param cable
     */
    @Insert("insert into cable (cable_number, cable_type, cable_tag_id, cable_area, cable_box_id)" +
            " values (#{cableNumber}, #{cableType}, #{cableTagId}, #{cableArea}, #{cableBoxId})")
    void insertCable(Cable cable);


    // update



}
