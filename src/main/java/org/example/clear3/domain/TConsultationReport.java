package org.example.clear3.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * TODO 人工上报实体类
 **/
@Data
@TableName("t_consultation_report")
public class TConsultationReport implements Serializable {

    /**
     * TODO
     **/
    @TableField("datadate")
    private Timestamp datadate;
    /**
     * TODO
     **/
    @TableField("citycode")
    private String citycode;
    /**
     * TODO
     **/
    @TableField("user_code")
    private String userCode;
    /**
     * TODO
     **/
    @TableField("aqi_max")
    private BigDecimal aqiMax;
    /**
     * TODO
     **/
    @TableField("aqi_min")
    private BigDecimal aqiMin;
    /**
     * TODO
     **/
    @TableField("aqi_max_level")
    private String aqiMaxLevel;
    /**
     * TODO
     **/
    @TableField("aqi_min_level")
    private String aqiMinLevel;
    /**
     * TODO
     **/
    @TableField("aqi_max_color")
    private String aqiMaxColor;
    /**
     * TODO
     **/
    @TableField("aqi_min_color")
    private String aqiMinColor;

    /**
     * TODO
     **/
    @TableField("aqi_max_type")
    private String aqiMaxType;
    /**
     * TODO
     **/
    @TableField("aqi_min_type")
    private String aqiMinType;
    /**
     * TODO
     **/
    @TableField("o3_8h_max")
    private BigDecimal o38hMax;
    /**
     * TODO
     **/
    @TableField("o3_8h_min")
    private BigDecimal o38hMin;
    /**
     * TODO
     **/
    @TableField("pm25_max")
    private BigDecimal pm25Max;
    /**
     * TODO
     **/
    @TableField("pm25_min")
    private BigDecimal pm25Min;
    /**
     * TODO
     **/
    @TableField("primary_pollutant")
    private String primaryPollutant;
    /**
     * TODO 预报时效
     **/
    @TableField("predictioninterval")
    private Double predictioninterval;
    /**
     * TODO
     **/
    @TableField("predictiontime")
    private Timestamp predictiontime;
    /**
     * TODO
     **/
    @TableField("create_time")
    private Timestamp createTime;

}