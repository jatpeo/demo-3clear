package org.example.clear3.domain.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @program: demo-3clear
 * @description: 城市日均查询
 * @author: Jiatp
 * @create: 2022-05-13 18:09
 **/
@Data
public class DayAverageParam extends BaseQueryParam {

    private String target= "so2_1h,no2_1h,pm10_1h,co_1h,o3_1h,pm25_1h,o3_8h";
}
