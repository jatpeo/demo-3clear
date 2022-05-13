package org.example.clear3.util;

import lombok.extern.slf4j.Slf4j;
import org.example.clear3.domain.TscPollutantcHour;
import org.example.clear3.domain.vo.AqiVO;
import org.example.clear3.enums.AQIEnum;
import org.example.clear3.exception.CustomException;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

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

    private static final String mask = "yes";

    /**
     * 同比月
     */
    public static final String TYPE_A = "TB_MONTH";

    /**
     * 环比日
     */
    public static final String TYPE_B = "HB_DAY";


    /**
     * @auther: Jiatp
     * @desp：// TODO: 计算每一项的同比,环比
     * @date: 2022/5/9 1:42 下午
     * @param: List<Map < String, Object>> currents,
     * @param: List<Map < String, Object>> currents,
     * @param: type 类型,
     * @return: List<Map < String, Object>>
     */
    public static List<Map<String, Object>> calMonthOver(List<Map<String, Object>> currents,
                                                         List<Map<String, Object>> lasts,
                                                         String type) throws ParseException {

        List<Map<String, Object>> maps = new LinkedList<>();
        for (Map<String, Object> curr : currents) {
            double so2C = (double) curr.get("so2");
            double no2C = (double) curr.get("no2");
            double pm10C = (double) curr.get("pm10");
            double pm25C = (double) curr.get("pm25");
            double coC = (double) curr.get("co");
            double o3C = (double) curr.get("o3");
            String datadate1 = (String) curr.get("datadate");
            String code1 = (String) curr.get("code");
            for (Map<String, Object> last : lasts) {
                String datadate2 = (String) last.get("datadate");
                String code2 = (String) last.get("code");
                String str = null;
                if (AqiUtils.TYPE_A.equals(type)) {
                    str = DateUtils.monthOverMonth(datadate2, DateUtils.ADD);
                } else if (AqiUtils.TYPE_B.equals(type)) {
                    str = DateUtils.calOverDay(datadate2, DateUtils.ADD);
                }
                if (code1.equals(code2) && datadate1.equals(str)) {
                    double so2L = (double) last.get("so2");
                    double no2L = (double) last.get("no2");
                    double pm10L = (double) last.get("pm10");
                    double pm25L = (double) last.get("pm25");
                    double coL = (double) last.get("co");
                    double o3L = (double) last.get("co");
                    //计算
                    String s02_rate = null;
                    String n02_rate = null;
                    String pm10_rate = null;
                    String pm25_rate = null;
                    String co_rate = null;
                    String o3_rate = null;
                    if (AqiUtils.TYPE_A.equals(type)) {
                        //计算同比月
                        s02_rate = ComCalUtil.calMonthOver(so2C, so2L);
                        n02_rate = ComCalUtil.calMonthOver(no2C, no2L);
                        pm10_rate = ComCalUtil.calMonthOver(pm10C, pm10L);
                        pm25_rate = ComCalUtil.calMonthOver(pm25C, pm25L);
                        co_rate = ComCalUtil.calMonthOver(coC, coL);
                        o3_rate = ComCalUtil.calMonthOver(o3C, o3L);
                    } else if (AqiUtils.TYPE_B.equals(type)) {
                        //计算环比日
                        s02_rate = ComCalUtil.calDayOver(so2C, so2L);
                        n02_rate = ComCalUtil.calDayOver(no2C, no2L);
                        pm10_rate = ComCalUtil.calDayOver(pm10C, pm10L);
                        pm25_rate = ComCalUtil.calDayOver(pm25C, pm25L);
                        co_rate = ComCalUtil.calDayOver(coC, coL);
                        o3_rate = ComCalUtil.calDayOver(o3C, o3L);
                    }
                    //set
                    curr.put("s02rate_" + type.toLowerCase(), s02_rate);
                    curr.put("n02rate_" + type.toLowerCase(), n02_rate);
                    curr.put("pm10rate_" + type.toLowerCase(), pm10_rate);
                    curr.put("pm25rate_" + type.toLowerCase(), pm25_rate);
                    curr.put("corate_" + type.toLowerCase(), co_rate);
                    curr.put("o3rate_" + type.toLowerCase(), o3_rate);
                    maps.add(curr);
                } else {
                    continue;
                }


            }
        }
        return maps;
    }


    /**
     * @param list
     * @Description: //TODO 重新整理List<map>>数据 根据日期排序
     * @Author: Jiatp
     * @Date: 2022/5/12 8:49 上午
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */

    public static List<Map<String, Object>> orderMapByDate(List<Map<String, Object>> list) {
        //排序
        if (list != null && list.size() > 1) {
            Collections.sort(list, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Integer o1Value = Integer.valueOf(o1.get("datadate").toString().replace(":", "")
                            .replace("-", "").replace(" ", "").substring(1, 8));
                    Integer o2Value = Integer.valueOf(o2.get("datadate").toString().replace(":", "")
                            .replace("-", "").replace(" ", "").substring(1, 8));
                    return o1Value.compareTo(o2Value);
                }
            });
        }
        return list;
    }

    /**
     * @param list
     * @Description: //TODO 重新整理List<map>>数据 aqi排序
     * @Author: Jiatp
     * @Date: 2022/5/12 8:50 上午
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    public static List<Map<String, Object>> orderMapByAqi(List<Map<String, Object>> list) {
        //排序
        if (list != null && list.size() > 1) {
            Collections.sort(list, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Double o1Value = Double.valueOf(o1.get("aqi").toString());
                    Double o2Value = Double.valueOf(o2.get("aqi").toString());
                    return o1Value.compareTo(o2Value);
                }
            });
        }
        return list;
    }

    /**
     * @param list
     * @Description: //TODO  排名信息
     * @Author: Jiatp
     * @Date: 2022/5/12 8:50 上午
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    public static List<Map<String, Object>> orderMapByLevel(List<Map<String, Object>> list) {
        //排序
        //处理aqi 排名问题
        int level = 1;
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            map.put("level", level++);
        }
        //set
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map1 = list.get(i);
            String level1 = map1.get("level").toString();
            String aqi1 = map1.get("aqi").toString();
            if (i + 1 < list.size()) {
                Map<String, Object> map2 = list.get(i + 1);
                String level2 = map2.get("level").toString();
                String aqi2 = map2.get("aqi").toString();
                if (aqi2.equals(aqi1)) {
                    map2.put("level", level1);
                }
            }
        }
        return list;
    }


    /**
     * @param day
     * @param ts
     * @Description: //TODO 计算城市的日均
     * @Author: Jiatp
     * @Date: 2022/5/12 8:50 上午
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     */
    public static Map<String, Object> getCityDayAvg(String day, List<TscPollutantcHour> ts) throws CustomException, ParseException {
        List<TscPollutantcHour> So2List = ts.stream().filter(o -> o.getSo21hMask().equals(mask)).collect(Collectors.toList());
        List<TscPollutantcHour> No2List = ts.stream().filter(o -> o.getNo21hMask().equals(mask)).collect(Collectors.toList());
        List<TscPollutantcHour> Pm10List = ts.stream().filter(o -> o.getPm101hMask().equals(mask)).collect(Collectors.toList());
        List<TscPollutantcHour> Pm25List = ts.stream().filter(o -> o.getPm251hMask().equals(mask)).collect(Collectors.toList());
        List<TscPollutantcHour> CoList = ts.stream().filter(o -> o.getCo1hMask().equals(mask)).collect(Collectors.toList());
        List<TscPollutantcHour> O3List = ts.stream().filter(o -> o.getO31hMask().equals(mask)).collect(Collectors.toList());
        Map<String, Double> aqiMap = new HashMap<>();
        Map<String, Object> result = new LinkedHashMap<>();
        //so2
        Double totalSo2 = So2List.stream().collect(Collectors.summingDouble(TscPollutantcHour::getSo21h));
        Double CSo2 = Double.valueOf(ComCalUtil.sciCal(totalSo2 / (So2List.size()), 0));
        //去求iaqi
        AqiVO iaqiSo2 = AqiUtils.getAQlTable("SO2", String.valueOf(CSo2));
        aqiMap.put("SO2", iaqiSo2.getIaqiValue());
        result.put("so2", CSo2);
        result.put("so2_aqi", String.valueOf(iaqiSo2.getIaqiValue()));
        //No2
        Double totalNo2 = No2List.stream().collect(Collectors.summingDouble(TscPollutantcHour::getNo21h));
        Double CNo2 = Double.valueOf(ComCalUtil.sciCal(totalNo2 / (No2List.size()), 0));
        AqiVO iaqiNo2 = AqiUtils.getAQlTable("NO2", String.valueOf(CNo2));
        aqiMap.put("NO2", iaqiNo2.getIaqiValue());
        result.put("no2", CNo2);
        result.put("no2_aqi", String.valueOf(iaqiNo2.getIaqiValue()));

        //Pm10
        Double totalPm10 = Pm10List.stream().collect(Collectors.summingDouble(TscPollutantcHour::getPm101h));
        Double CPm10 = Double.valueOf(ComCalUtil.sciCal(totalPm10 / (Pm10List.size()), 0));
        //去求浓度
        AqiVO iaqiPm10 = AqiUtils.getAQlTable("PM10", String.valueOf(CPm10));
        aqiMap.put("PM10", iaqiPm10.getIaqiValue());
        result.put("pm10", CPm10);
        result.put("pm10_aqi", String.valueOf(iaqiPm10.getIaqiValue()));

        //Pm25
        Double totalPm25 = Pm25List.stream().collect(Collectors.summingDouble(TscPollutantcHour::getPm251h));
        Double CPm25 = Double.valueOf(ComCalUtil.sciCal(totalPm25 / (Pm25List.size()), 0));
        //去求浓度
        AqiVO iaqiPm25 = AqiUtils.getAQlTable("PM2.5", String.valueOf(CPm25));
        aqiMap.put("PM25", iaqiPm25.getIaqiValue());
        result.put("pm25", CPm25);
        result.put("pm25_aqi", String.valueOf(iaqiPm25.getIaqiValue()));

        //CO
        Double totalCo = CoList.stream().collect(Collectors.summingDouble(TscPollutantcHour::getCo1h));
        Double Cco = totalCo / (CoList.size());
        //去求浓度
        AqiVO iaqiCo = AqiUtils.getAQlTable("CO", String.valueOf(Cco));
        aqiMap.put("CO", iaqiCo.getIaqiValue());
        result.put("co", Math.ceil(Cco));
        result.put("co_aqi", String.valueOf(iaqiCo.getIaqiValue()));

        //O3
        Double totalO3 = O3List.stream().collect(Collectors.summingDouble(TscPollutantcHour::getO31h));
        Double Co3 = totalO3 / (O3List.size());
        //去求浓度
        AqiVO iaqiO3 = AqiUtils.getAQlTable("O3", String.valueOf(Co3));
        aqiMap.put("O3", iaqiO3.getIaqiValue());
        result.put("o3", Math.ceil(Co3));
        result.put("o3_aqi", String.valueOf(iaqiO3.getIaqiValue()));

        //03 滑动8小时
        List<TscPollutantcHour> collect = O3List.stream().sorted(Comparator.comparing(TscPollutantcHour::getMonitorDate)).collect(Collectors.toList());
        List<Double> O38hList = collect.stream().map(TscPollutantcHour::getO31h).collect(Collectors.toList());
        Double[] o3Arr = O38hList.stream().map(Double::valueOf).toArray(Double[]::new);
        List<Double> avg8h = O3Utils.get8Avg(o3Arr);
        Double maxAvg = O3Utils.get8MaxAvg(avg8h);
        result.put("o3_8h_max", maxAvg);

        //该天最大的aqi
        Optional<Map.Entry<String, Double>> maxAqi = aqiMap.entrySet().stream()
                .collect(Collectors.maxBy(Map.Entry.comparingByValue()));
        Map.Entry<String, Double> maxValue = maxAqi.get();
        result.put("primary_pollutant", maxValue.getKey());
        result.put("aqi", String.valueOf(maxValue.getValue()));
        result.put("aqi_mask", "yes");
        result.put("datadate", day);
        return result;
    }

    /**
     * @param type
     * @param value
     * @Description: //TODO 计算iaqi方法
     * @Author: Jiatp
     * @Date: 2022/5/12 8:51 上午
     * @return: org.example.clear3.domain.vo.AqiVO
     */
    public static AqiVO getAQlTable(String type, String value) throws CustomException {
        Map<String, Integer[]> maps = initAqlTable();
        //空气质量分指数
        Integer[] iaqi = maps.get("IAQI");
        Integer[] orgins = maps.get(type);
        double c = 0.0;
        try {
            c = Double.valueOf(value);
            if (c < orgins[0] || c > orgins[orgins.length - 1]) {
                log.error(type + "浓度值不在允许范围之内!");
                throw new CustomException(type + "浓度值不在允许范围之内!");
            }
        } catch (Exception e) {
            throw new CustomException("浓度值异常!");
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
     * @param
     * @Description: //TODO 初始化24小时
     * @Author: Jiatp
     * @Date: 2022/5/12 8:51 上午
     * @return: java.util.Map<java.lang.String, java.lang.Integer [ ]>
     */
    public static Map<String, Integer[]> initAqlTable() {
        Map<String, Integer[]> maps = new HashMap<>(7);
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
