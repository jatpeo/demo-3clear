package org.example.clear3.util;

import cn.hutool.core.map.MapUtil;
import org.example.clear3.domain.TscPollutantcHour;
import org.example.clear3.domain.vo.AqiVO;
import org.example.clear3.exception.CustomException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * @program: demo-3clear
 * @description: 工具类
 * @author: Jiatp
 * @create: 2022-05-09 12:04
 **/
public class ComCalUtil {


    /**
     * @param value  集合
     * @param target 指标类型
     * @Description: //TODO 计算出每个同一个城市，不同天的时段平均浓度值
     * @Author: Jiatp
     * @Date: 2022/5/13 11:05 上午
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     */
    public static Map<String, Object> getCityTimeValue(List<Map> value, String target, String monitorHour,
                                                       String cityCode, String cityName) throws Exception {

        //返回的结果集
        Map<String, Object> map = MapUtil.createMap(LinkedHashMap.class);
        double average = value.stream().mapToDouble(e -> new Double(String.valueOf(e.get(target)))).average().orElse(0);
        //计算平均浓度
        String result = "-";
        String iAqi = "-";
        if (-999.0 != average) {
            //计算iaqi
            result = "co_1h".equals(target) ? sciCal(average, 1) : sciCal(average, 0);
            AqiVO aqiVO = AqiUtils.getAQlTable(target.split("_")[0].toUpperCase(), result);
            iAqi = sciCal(aqiVO.getIaqiValue(), 0);
        }
        map.put("code", cityCode);
        map.put("name", cityName);
        map.put("monitorhour", monitorHour);
        map.put(target, result);
        map.put(target + "_iaqi", iAqi);
        return map;
    }


    /**
     * @param list
     * @param target
     * @Description: //TODO 计算每个污染的 浓度和iaqi，臭氧单独计算
     * @Author: Jiatp
     * @Date: 2022/5/12 7:06 下午
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     */
    public static Map<String, Object> getCityDayAvg(List<Map> list, String target) throws CustomException {

        Map<String, Object> map = MapUtil.newHashMap();
        double average = 0.0;
        if ("o3_1h".equals(target)) {
            List<Double> O38hList = list.stream().map(o -> {
                return Double.valueOf(o.get("o3_1h").toString());
            }).collect(Collectors.toList());
            Double[] array = O38hList.stream().map(Double::valueOf).toArray(Double[]::new);
            List<Double> avg8h = O3Utils.get8Avg(array);
            average = O3Utils.get8MaxAvg(avg8h);
            map.put(target + "_max", sciCal(average, 0));
        } else {
            average = list.stream().mapToDouble(e -> new Double(String.valueOf(e.get(target)))).average().orElse(0);
            map.put(target, sciCal(average, 0));
        }
        //()
        AqiVO aqiVO = AqiUtils.getAQlTable(target.split("_")[0].toUpperCase(), String.valueOf(average));
        map.put(target + "_iaqi", sciCal(aqiVO.getIaqiValue(), 0));
        return map;


    }


    /**
     * @param value 需要科学计算的数据
     * @param digit 保留的小数位
     * @return 功能：四舍六入五成双计算法
     */
    public static String sciCal(double value, int digit) {
        String result = "-999";
        try {
            double ratio = Math.pow(10, digit);
            double _num = value * ratio;
            double mod = _num % 1;
            double integer = Math.floor(_num);
            double returnNum;
            if (mod > 0.5) {
                returnNum = (integer + 1) / ratio;
            } else if (mod < 0.5) {
                returnNum = integer / ratio;
            } else {
                returnNum = (integer % 2 == 0 ? integer : integer + 1) / ratio;
            }
            BigDecimal bg = new BigDecimal(returnNum);
            result = bg.setScale((int) digit, BigDecimal.ROUND_HALF_UP).toString();
        } catch (RuntimeException e) {
            throw e;
        }
        return result;
    }


    /**
     * @auther: Jiatp
     * @desp：// TODO: 计算环比 日
     * @date: 2022/5/9 3:30 下午
     * @param: @param	null
     * @return: String
     */
    public static String calDayOver(double curr, double last) {
        double value = (curr - last) / last;
        String result = ComCalUtil.sciCal(value * 100, 0) + "%";
        return result;
    }

    /**
     * @auther: Jiatp
     * @desp：// TODO: 计算同比的 月
     * @date: 2022/5/9 3:30 下午
     * @param: @param	null
     * @return: String
     */
    public static String calMonthOver(double curr, double last) {
        double value = (curr - last) / last;
        String result = ComCalUtil.sciCal(value * 100, 0) + "%";
        return result;
    }

}

