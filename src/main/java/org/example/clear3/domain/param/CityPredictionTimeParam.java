package org.example.clear3.domain.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @program: demo-3clear
 * @description: 城市逐日预报请求参数
 * @author: Jiatp
 * @create: 2022-05-16 10:45
 **/
@Data
public class CityPredictionTimeParam {

    /**
     * TODO 起报时间
     **/
    @NotBlank(message = "起报时间不能为空")
    private String predictionTime;
    /**
     * TODO 预报时长
     **/
    @NotBlank(message = "预报时长不能为空")
    private String predictionLong;
    /**
     * TODO 城市编码
     **/
    @NotBlank(message = "城市编码不能为空")
    private String cityCode;
    /**
     * TODO 预报区域
     **/
    @NotBlank(message = "预报区域不能为空")
    private String zoneName;
    /**
     * TODO 预报指标
     **/
    @NotBlank(message = "预报指标不能为空")
    private String target;
    /**
     * TODO 时间类型 day/hour
     **/
    @NotBlank(message = "时间类型不能为空")
    private String dateType;
    /**
     * TODO 模式名称
     **/
    @NotBlank(message = "模式名称不能为空")
    private String modelName;





}
