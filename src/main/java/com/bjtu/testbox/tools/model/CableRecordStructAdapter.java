package com.bjtu.testbox.tools.model;

import com.bjtu.testbox.entity.CableRecord;

import java.io.Serializable;
import java.util.List;

/**
 * @program: testbox
 * @description: 适配器
 * @author: Mr.Bruin
 * @create: 2020-08-23 01:14
 **/
public class CableRecordStructAdapter implements Serializable {
    private List<CableRecord> data;

    public List<CableRecord> getData() {
        return data;
    }

    public void setData(List<CableRecord> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CableRecordStructAdapter{" +
                "data=" + data +
                '}';
    }
}
