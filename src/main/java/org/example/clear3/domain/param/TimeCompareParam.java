package org.example.clear3.domain.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @program: demo-3clear
 * @description: 时段对比分析param
 * @author: Jiatp
 * @create: 2022-05-13 10:38
 **/
@Data
public class TimeCompareParam extends BaseQueryParam{

    @NotBlank(message = "类型不能为空")
    private String target;

}
