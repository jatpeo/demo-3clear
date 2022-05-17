package org.example.clear3.domain.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @program: demo-3clear
 * @description: 预报人工上报数据
 * @author: Jiatp
 * @create: 2022-05-17 11:28
 **/
@Data
public class ConsultationReportParam {

    //起报时间
    @NotBlank(message = "起报时间不能为空")
    private String predictiontime;

    //起报开始时间
    @NotBlank(message = "监测时间不能为空")
    private String datadate;

    /**
     * TODO aqi最大
     **/
    @NotBlank(message = "aqi最大不能为空")
    private String aqiMax;
    /**
     * TODO aqi最小
     **/
    @NotBlank(message = "aqi最小不能为空")
    private String aqiMin;
    /**
     * TODO 主要污染物
     **/
    @NotBlank(message = "污染物不能为空")
    private String primaryPollutant;
    /**
     * TODO o38h 最大
     **/
    @NotBlank(message = "o38h最大不能为空")
    private String o38hMax;
    /**
     * TODO o38h 最小
     **/
    @NotBlank(message = "o38h最小不能为空")
    private String o38hMin;
    /**
     * TODO pm2.5最大
     **/
    @NotBlank(message = "pm2.5最小不能为空")
    private String pm25Min;
    /**
     * TODO pm2.5最小
     **/
    @NotBlank(message = "pm2.5最大不能为空")
    private String pm25Max;







}
