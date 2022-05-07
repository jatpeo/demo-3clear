package org.example.clear3.util;

import lombok.extern.slf4j.Slf4j;
import org.example.clear3.domain.vo.AqiVO;
import org.example.clear3.enums.AQIEnum;
import org.example.clear3.exception.CustomException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Jiatp  11:06 上午 2022/5/6
 * @Description //TODO AQl限定
 * @Param
 * @return
 **/
@Slf4j
public class AqiUtils {

    /**
     * IAQI最大
     **/
    private static Integer l_height = 0;
    /**
     * IAQI最小
     **/
    private static Integer l_low = 0;
    /**
     * C最大
     **/
    private static Integer c_height = 0;
    /**
     * C最小
     **/
    private static Integer c_low = 0;


    /**
     * @return
     * @Author Jiatp  11:09 上午 2022/5/6
     * @Description //TODO 得到24h对应表
     * @Param
     **/
    public static AqiVO getAQlTable(String type, String value) throws CustomException {
        Map<String, Integer[]> maps = initAqlTable();
        //空气质量分指数
        Integer[] iaqi = maps.get("IAQI");
        Integer[] orgins = maps.get(type);
        double c = 0.0;
        try {
            c = Double.valueOf(value);
        } catch (Exception e) {
            throw new CustomException("浓度值异常!");
        }
        if (c < orgins[0] || c > orgins[orgins.length - 1]) {
            log.error(type + "浓度值不在允许范围之内!");
            throw new CustomException(type + "浓度值不在允许范围之内!");
        }
        //确定浓度的最大最小
        for (int i = 0; i < orgins.length; i++) {
            if (c <= orgins[i]) {
                c_height = orgins[i];
                c_low = orgins[i - 1];

                l_height = iaqi[i];
                l_low = iaqi[i - 1];
                break;
            }
        }
        log.info(type + "-》浓度最大：" + c_height + "\t 最小：" + c_low);
        log.info(type + "-》iaqi最大：" + l_height + "\t 最小：" + l_low);

        //计算IAQI
        double a1 = l_height - l_low;
        double a2 = c_height - c_low;
        double iaqiValue = Math.ceil(a1 / a2 * (c - c_low) + l_low);
        String[] result = AQIEnum.getName(iaqiValue).split(",");
        //封装实体
        AqiVO aqiVo = new AqiVO();
        aqiVo.setIaqiValue(iaqiValue);
        aqiVo.setLevelDesp(result[0]);
        aqiVo.setLevelColor(result[1]);
        aqiVo.setLevelValue(result[2]);
        return aqiVo;
    }

    /**
     * @return
     * @Author Jiatp  11:30 上午 2022/5/6
     * @Description //TODO 初始化24小时
     * @Param
     **/
    public static Map<String, Integer[]> initAqlTable() {
        Map<String, Integer[]> maps = new HashMap<>();
        //类型对应24h的值
        Integer[] IAQI = {0, 50, 100, 150, 200, 300, 400, 500};
        Integer[] PM25 = {0, 35, 75, 115, 150, 250, 350, 500};
        Integer[] PM10 = {0, 50, 150, 250, 350, 420, 500, 600};
        Integer[] SO2 = {0, 50, 150, 475, 800, 1600, 2100, 2620};
        Integer[] NO2 = {0, 40, 80, 180, 280, 565, 750, 940};
        Integer[] CO = {0, 2, 4, 14, 24, 36, 48, 60};
        Integer[] O3 = {0, 100, 160, 215, 265, 800};
        maps.put("IAQI", IAQI);
        maps.put("PM2.5", PM25);
        maps.put("PM10", PM10);
        maps.put("SO2", SO2);
        maps.put("NO2", NO2);
        maps.put("CO", CO);
        maps.put("O3", O3);
        return maps;
    }
}
