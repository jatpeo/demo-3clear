package org.example.clear3.enums;

/**
 * @Author Jiatp
 * @Description //TODO  污染类型
 * @Date 4:58 下午 2022/5/6
 **/
public enum AQITypeEnum {

    PM25("PM2.5"),
    PM10("PM10"),
    SO2("SO2"),
    NO2("NO2"),
    CO("CO"),
    O3("O3"),
    unKnow("未知");

    private String name;

    private AQITypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 转换结果
     */
    public static String getName(String name) {

        if (name.equals(AQITypeEnum.PM25.name)) {
            return AQITypeEnum.PM10.name;
        } else if (name.equals(AQITypeEnum.PM10.name)) {
            return AQITypeEnum.PM10.name;
        } else if (name.equals(AQITypeEnum.SO2.name)) {
            return AQITypeEnum.PM10.name;
        } else if (name.equals(AQITypeEnum.NO2.name)) {
            return AQITypeEnum.PM10.name;
        } else if (name.equals(AQITypeEnum.CO.name)) {
            return AQITypeEnum.PM10.name;
        } else if (name.equals(AQITypeEnum.O3.name)) {
            return AQITypeEnum.PM10.name;
        } else {
            return "未知";
        }
    }

}
