package org.example.clear3.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Jiatp
 * @Description //TODO
 * @Date 7:28 下午 2022/5/6
 **/
@Data
@TableName("t_sc_pollutant_c_hour")
public class TscPollutantcHour implements Serializable {

    @TableField("monitordate")
    private Date monitorDate;

    /** **/
    @TableField("citycode")
    private String cityCode;

    /** **/
    @TableField("citytype")
    private String cityType;

    /** **/
    @TableField("so2_1h")
    private Double so21h;

    /** **/
    @TableField("so2_1h_iaqi")
    private Double so21hIaqi;

    /** **/
    @TableField("so2_1h_mask")
    private String so21hMask;

    /** **/
    @TableField("no2_1h")
    private Double no21h;

    /** **/
    @TableField("no2_1h_iaqi")
    private Double no21hIaqi;

    /** **/
    @TableField("no2_1h_mask")
    private String no21hMask;

    /** **/
    @TableField("pm10_1h")
    private Double pm101h;

    /** **/
    @TableField("pm10_1h_iaqi")
    private Double pm101hIaqi;

    /** **/
    @TableField("pm10_1h_mask")
    private String pm101hMask;

    /** **/
    @TableField("pm10_24h")
    private Double pm1024h;

    /** **/
    @TableField("pm10_24h_iaqi")
    private Double pm1024hIaqi;

    /** **/
    @TableField("pm10_24h_mask")
    private String pm1024hMask;

    /** **/
    @TableField("co_1h")
    private Double co1h;

    /** **/
    @TableField("co_1h_iaqi")
    private Double co1hIaqi;

    /** **/
    @TableField("co_1h_mask")
    private String co1hMask;

    /** **/
    @TableField("o3_1h")
    private Double o31h;

    /** **/
    @TableField("o3_1h_iaqi")
    private Double o31hIaqi;

    /** **/
    @TableField("o3_1h_mask")
    private String o31hMask;

    /** **/
    @TableField("o3_8h")
    private Double o38h;

    /** **/
    @TableField("o3_8h_iaqi")
    private Double o38hIaqi;

    /** **/
    @TableField("o3_8h_mask")
    private String o38hMask;

    /** **/
    @TableField("pm25_1h")
    private Double pm251h;

    /** **/
    @TableField("pm25_1h_iaqi")
    private Double pm251hIaqi;

    /** **/
    @TableField("pm25_1h_mask")
    private String pm251hMask;

    /** **/
    @TableField("pm25_24h")
    private Double pm2524h;

    /** **/
    @TableField("pm25_24h_iaqi")
    private Double pm2524hIaqi;

    /** **/
    @TableField("pm25_24h_mask")
    private String pm2524hMask;

    /** **/
    @TableField("aqi")
    private Double aqi;

    /** **/
    @TableField("primary_pollutant")
    private String primaryPollutant;

    /** **/
    @TableField("aqi_level")
    private String aqiLevel;

    /** **/
    @TableField("aqi_mask")
    private String aqiMask;

    /** **/
    @TableField("no_1h")
    private Double no1h;

    /** **/
    @TableField("no_1h_mask")
    private String no1hMask;

    /** **/
    @TableField("nox_1h")
    private Double nox1h;

    /** **/
    @TableField("nox_1h_mask")
    private String nox1hMask;


    public String transDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);
        return format;
    }
}
