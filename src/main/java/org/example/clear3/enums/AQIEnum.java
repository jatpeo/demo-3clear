package org.example.clear3.enums;

import org.example.clear3.constant.AqiContants;

/**
 * @Author Jiatp
 * @Description //TODO
 * @Date 1:55 下午 2022/5/6
 **/
public enum AQIEnum {

    GREEN("优", "绿色", "#008000", "一级"),
    YELLOW("良", "黄色", "#FFFFCC", "二级"),
    ORANGE("轻度污染", "橙色", "#FF6633", "三级"),
    RED("中度污染", "红色", "#FF0000", "四级"),
    PURPLE("重度污染", "紫色", "#CC3299", "五级"),
    MAROON("严重污染", "褐红色", "#4E2F2F", "六级");

    private String desc;
    private String color;
    private String value;
    private String level;

    private AQIEnum(String desc, String color, String value, String level) {
        this.color = color;
        this.desc = desc;
        this.value = value;
        this.level = level;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public String getColor() {
        return color;
    }

    public String getLevel() {
        return level;
    }

    /**
     * @param value
     * @Description: //TODO 返回aqi基本信息
     * @Author: Jiatp
     * @Date: 2022/5/16 5:45 下午
     * @return: java.lang.String
     */
    public static String getName(double value) {
        if (value >= AqiContants.AQIBASEVALUE[0] && value <= AqiContants.AQIBASEVALUE[1]) {
            return AQIEnum.GREEN.desc + "," + AQIEnum.GREEN.color + "," + AQIEnum.GREEN.value + "," + AQIEnum.GREEN.level;
        } else if (value >= AqiContants.AQIBASEVALUE[2] && value <= AqiContants.AQIBASEVALUE[3]) {
            return AQIEnum.YELLOW.desc + "," + AQIEnum.YELLOW.color + "," + AQIEnum.YELLOW.value + "," + AQIEnum.YELLOW.level;
        } else if (value >= AqiContants.AQIBASEVALUE[4] && value <= AqiContants.AQIBASEVALUE[5]) {
            return AQIEnum.ORANGE.desc + "," + AQIEnum.ORANGE.color + "," + AQIEnum.ORANGE.value + "," + AQIEnum.ORANGE.level;
        } else if (value >= AqiContants.AQIBASEVALUE[6] && value <= AqiContants.AQIBASEVALUE[7]) {
            return AQIEnum.RED.desc + "," + AQIEnum.RED.color + "," + AQIEnum.RED.value + "," + AQIEnum.RED.level;
        } else if (value >= AqiContants.AQIBASEVALUE[8] && value < AqiContants.AQIBASEVALUE[9]) {
            return AQIEnum.PURPLE.desc + "," + AQIEnum.PURPLE.color + "," + AQIEnum.PURPLE.value + "," + AQIEnum.PURPLE.level;
        } else if (value >= AqiContants.AQIBASEVALUE[9]) {
            return AQIEnum.MAROON.desc + "," + AQIEnum.MAROON.color + "," + AQIEnum.MAROON.value + "," + AQIEnum.MAROON.level;
        }
        return null;
    }
}
