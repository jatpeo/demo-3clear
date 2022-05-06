package org.example.clear3.enums;

/**
 * @Author Jiatp
 * @Description //TODO
 * @Date 1:55 下午 2022/5/6
 **/
public enum AQIEnum {

    GREEN("优", "绿色", "#008000"),
    YELLOW("良", "黄色", "#FFFFCC"),
    ORANGE("轻度污染", "橙色", "#FF6633"),
    RED("中度污染", "红色", "#FF0000"),
    PURPLE("重度污染", "紫色", "#CC3299"),
    MAROON("严重污染", "褐红色", "#4E2F2F");

    private String desc;
    private String color;
    private String value;

    private AQIEnum(String desc, String color, String value) {
        this.color = color;
        this.desc = desc;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * 转换结果
     *
     * @param value
     * @return
     */
    public static String getName(double value) {
        if (value >= 0 && value <= 50) {
            return AQIEnum.GREEN.desc + "," + AQIEnum.GREEN.color + "," + AQIEnum.GREEN.value;
        } else if (value > 51 && value <= 100) {
            return AQIEnum.YELLOW.desc + "," + AQIEnum.YELLOW.color + "," + AQIEnum.YELLOW.value;
        } else if (value > 101 && value <= 150) {
            return AQIEnum.ORANGE.desc + "," + AQIEnum.ORANGE.color + "," + AQIEnum.ORANGE.value;
        } else if (value > 151 && value <= 200) {
            return AQIEnum.RED.desc + "," + AQIEnum.RED.color + "," + AQIEnum.RED.value;
        } else if (value > 201 && value < 300) {
            return AQIEnum.PURPLE.desc + "," + AQIEnum.PURPLE.color + "," + AQIEnum.PURPLE.value;
        } else if (value >= 300) {
            return AQIEnum.MAROON.desc + "," + AQIEnum.MAROON.color + "," + AQIEnum.MAROON.value;
        }
        return null;
    }
}
