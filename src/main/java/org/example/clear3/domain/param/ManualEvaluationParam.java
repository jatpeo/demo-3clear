package org.example.clear3.domain.param;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @program: demo-3clear
 * @description: 人工预报评估接口参数
 * @author: Jiatp
 * @create: 2022-05-18 15:28
 **/
@Data
@ToString
public class ManualEvaluationParam {

    /**
     * TODO 开始时间
     **/
    @NotBlank(message = "开始时间不能为空")
    private String beginDataDate;

    /**
     * TODO 结束
     **/
    @NotBlank(message = "结束时间不能为空")
    private String endDataDate;

    /**
     * TODO 城市编码
     **/
    @NotBlank(message = "城市编码不能为空")
    private String cityCode;
}
