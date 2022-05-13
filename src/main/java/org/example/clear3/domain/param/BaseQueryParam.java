package org.example.clear3.domain.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @program: demo-3clear
 * @description: 基本查询参数信息
 * @author: Jiatp
 * @create: 2022-05-12 11:52
 **/
@Data
@ToString
public class BaseQueryParam {


    @NotBlank(message = "城市不能为空")
    private String cityCode;

    @NotBlank(message = "开始时间不能为空")
    private String beginTimeStr;

    @NotBlank(message = "结束时间不能为空")
    private String endTimeStr;




    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getBeginTimeStr() {
        return beginTimeStr;
    }

    public void setBeginTimeStr(String beginTimeStr) {
        this.beginTimeStr = beginTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }
}
