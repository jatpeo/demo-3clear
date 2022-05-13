package org.example.clear3.domain.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @program: demo-3clear
 * @description: 单个指标查询
 * @author: Jiatp
 * @create: 2022-05-13 10:40
 **/
@Data
public class SingleTypeParam extends BaseQueryParam {

    @NotBlank(message = "类型不能为空")
    private String target;
}
