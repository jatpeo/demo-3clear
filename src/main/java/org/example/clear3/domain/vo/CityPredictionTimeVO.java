package org.example.clear3.domain.vo;

import lombok.Data;

/**
 * @program: demo-3clear
 * @description: 城市逐日预报vo
 * @author: Jiatp
 * @create: 2022-05-16 11:22
 **/
@Data
public class CityPredictionTimeVO {

    /**
     * TODO 动态表名
     **/
    private String tableName;
    /**
     * TODO 指标参数
     **/
    private String target;
    /**
     * TODO 城市编码
     **/
    private String cityCode;
    /**
     * TODO 起报时间
     **/
    private String predictionTime;
    /**
     * TODO 时长开始时间
     **/
    private String startDataDate;
    /**
     * TODO 时长结束时间
     **/
    private String endDataDate;


}
