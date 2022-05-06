package org.example.clear3.domain.vo;


import lombok.Data;

/**
 * @Author Jiatp  10:27 上午 2022/5/6
 * @Description //TODO 返回的AQIVO
 * @Param
 * @return
 **/
@Data
public class AQIVo {

    /**
     * 污染物对应的IAQI值
     */
    private Double IaqiValue;

    /**
     * AQi对应的污染等级
     */
    private String levelValue;

    /**
     * 污染等级的文字描述
     */
    private String levelDesp;

    /**
     * 污染等级对应的颜色
     */
    private String levelColor;

}
