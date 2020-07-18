package com.bjtu.testbox.mapper;

import com.bjtu.testbox.entity.Worker;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerMapper {
    // select

    /**
     * @param workerNumber
     * @return a worker
     */
    Worker selectByNumber(@Param("workerNumber") int workerNumber);

    // insert

    /**
     * insert a worker
     * @param worker
     */
    void insertWorker(Worker worker);

    // update

    /**
     * update basic info of a worker
     * @param workerId
     * @param workerName
     * @param workerArea
     * @param workerGroup
     * @param workerCity
     * @param workerPhone
     */
    void updateWorkerInfo(@Param("workerId") int workerId, @Param("workerName") String workerName,
                          @Param("workerArea") String workerArea, @Param("workerGroup") String workerGroup,
                          @Param("workerCity") String workerCity, @Param("workerPhone") String workerPhone);
}
