package org.example.clear3.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.clear3.domain.TscPollutantcHour;
import org.example.clear3.domain.vo.AqiVO;
import org.example.clear3.exception.CustomException;
import org.example.clear3.enums.AQITypeEnum;
import org.example.clear3.mapper.TscPollutantcHourMapper;
import org.example.clear3.util.AqiUtils;
import org.example.clear3.util.O3Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author Jiatp
 * @Description //TODO aqi计算类
 * @Date 5:46 下午 2022/5/7
 **/
@Service
@Slf4j
public class AqiServiceImpl implements AqiService {


    @Autowired
    TscPollutantcHourMapper tScPollutantcHourMapper;


    /**
     * @return List<Map<String, Object>>
     * @Author Jiatp
     * @Description //TODO 计算多城市日均
     * @Date 3:31 下午 2022/5/7
     * @Param timeRange,cityCode
     **/
    @Override
    public List<Map<String, Object>> getAqiAvg(String timeRange, String cityCode) throws CustomException, ParseException {

        //time
        String[] times = timeRange.split(",");
        Date begin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(times[0]);
        Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(times[1]);
        List<String> citys = Arrays.asList(cityCode.split(","));
        //select
        QueryWrapper<TscPollutantcHour> wrapper = new QueryWrapper<>();
        wrapper.in("citycode", citys);
        wrapper.between("monitordate", begin, end);
        List<TscPollutantcHour> tscPollutantcHours = tScPollutantcHourMapper.selectList(wrapper);

        //按照城市分组
        Map<String, List<TscPollutantcHour>> groupMap = tscPollutantcHours.stream().collect(Collectors.groupingBy(TscPollutantcHour::getCityCode));

        // List<TscPollutantcHour> ccTime = new ArrayList<>();

        List<Map<String, Object>> rS = new ArrayList<>();
        for (Map.Entry<String, List<TscPollutantcHour>> entry : groupMap.entrySet()) {
            String city = entry.getKey();
            List<TscPollutantcHour> value = entry.getValue();
            //按照城市的时间分组
            Map<String, List<TscPollutantcHour>> collect = value.stream().sorted(Comparator.comparing(TscPollutantcHour::getMonitorDate))
                    .collect(Collectors.groupingBy(o -> o.transDate(o.getMonitorDate())));
            for (Map.Entry<String, List<TscPollutantcHour>> entry2 : collect.entrySet()) {
                System.out.println("key= " + entry2.getKey() + " and value= " + entry2.getValue());
                //当天
                String day = entry2.getKey();
                List<TscPollutantcHour> ts = entry2.getValue();

                Map<String, Object> result = transAqi(day, ts);
                result.put("code", city);
                rS.add(result);
            }
        }
        return rS;
    }

    /**
     * @return Map<String, Object>
     * @Author Jiatp
     * @Description //TODO 计算多城市计算逻辑
     * @Date 3:29 下午 2022/5/7
     * @Param day,ts
     **/
    public Map<String, Object> transAqi(String day, List<TscPollutantcHour> ts) throws CustomException, ParseException {

        List<TscPollutantcHour> So2List = ts.stream().filter(o -> o.getSo21hMask().equals("yes")).collect(Collectors.toList());
        List<TscPollutantcHour> No2List = ts.stream().filter(o -> o.getNo21hMask().equals("yes")).collect(Collectors.toList());
        List<TscPollutantcHour> Pm10List = ts.stream().filter(o -> o.getPm101hMask().equals("yes")).collect(Collectors.toList());
        List<TscPollutantcHour> Pm25List = ts.stream().filter(o -> o.getPm251hMask().equals("yes")).collect(Collectors.toList());
        List<TscPollutantcHour> CoList = ts.stream().filter(o -> o.getCo1hMask().equals("yes")).collect(Collectors.toList());
        List<TscPollutantcHour> O3List = ts.stream().filter(o -> o.getO31hMask().equals("yes")).collect(Collectors.toList());
        Map<String, Double> aqiMap = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        //so2
        Double totalSo2 = So2List.stream().collect(Collectors.summingDouble(TscPollutantcHour::getSo21h));
        Double CSo2 = totalSo2 / (So2List.size());
        //去求浓度
        AqiVO iaqiSo2 = AqiUtils.getAQlTable("SO2", String.valueOf(CSo2));
        aqiMap.put("SO2", iaqiSo2.getIaqiValue());
        result.put("so2_aqi", String.valueOf(iaqiSo2.getIaqiValue()));

        //No2
        Double totalNo2 = No2List.stream().collect(Collectors.summingDouble(TscPollutantcHour::getNo21h));
        Double CNo2 = totalNo2 / (No2List.size());
        //去求浓度
        AqiVO iaqiNo2 = AqiUtils.getAQlTable("NO2", String.valueOf(CNo2));
        aqiMap.put("NO2", iaqiNo2.getIaqiValue());
        result.put("no2_aqi", String.valueOf(iaqiNo2.getIaqiValue()));

        //Pm10
        Double totalPm10 = Pm10List.stream().collect(Collectors.summingDouble(TscPollutantcHour::getPm101h));
        Double CPm10 = totalPm10 / (Pm10List.size());
        //去求浓度
        AqiVO iaqiPm10 = AqiUtils.getAQlTable("PM10", String.valueOf(CPm10));
        aqiMap.put("PM10", iaqiPm10.getIaqiValue());
        result.put("pm10_aqi", String.valueOf(iaqiPm10.getIaqiValue()));

        //Pm25
        Double totalPm25 = Pm25List.stream().collect(Collectors.summingDouble(TscPollutantcHour::getPm251h));
        Double CPm25 = totalPm25 / (Pm25List.size());
        //去求浓度
        AqiVO iaqiPm25 = AqiUtils.getAQlTable("PM2.5", String.valueOf(CPm25));
        aqiMap.put("PM25", iaqiPm25.getIaqiValue());
        result.put("pm25_aqi", String.valueOf(iaqiPm25.getIaqiValue()));

        //CO
        Double totalCo = CoList.stream().collect(Collectors.summingDouble(TscPollutantcHour::getCo1h));
        Double Cco = totalCo / (CoList.size());
        //去求浓度
        AqiVO iaqiCo = AqiUtils.getAQlTable("CO", String.valueOf(Cco));
        aqiMap.put("CO", iaqiCo.getIaqiValue());
        result.put("pmco_aqi", String.valueOf(iaqiCo.getIaqiValue()));


        //O3
        Double totalO3 = O3List.stream().collect(Collectors.summingDouble(TscPollutantcHour::getO31h));
        Double Co3 = totalO3 / (O3List.size());
        //去求浓度
        AqiVO iaqiO3 = AqiUtils.getAQlTable("O3", String.valueOf(Co3));
        aqiMap.put("O3", iaqiO3.getIaqiValue());
        result.put("o3_aqi", String.valueOf(iaqiO3.getIaqiValue()));

        //该天最大的aqi
        Optional<Map.Entry<String, Double>> maxAqi = aqiMap.entrySet().stream()
                .collect(Collectors.maxBy(Map.Entry.comparingByValue()));
        Map.Entry<String, Double> maxValue = maxAqi.get();
        result.put("primary_pollutant", maxValue.getKey());
        result.put("aqi", String.valueOf(maxValue.getValue()));
        result.put("datadate", day);
        result.put("aqi_mask", "yes");

        //03
        List<TscPollutantcHour> collect = O3List.stream().sorted(Comparator.comparing(TscPollutantcHour::getMonitorDate)).collect(Collectors.toList());
        List<Double> O38hList = collect.stream().map(TscPollutantcHour::getO31h).collect(Collectors.toList());
        Object[] objects = O38hList.toArray();
        double[] o3Arr = new double[24];
        for (int i = 0; i < objects.length; i++) {
            o3Arr[i] = (double)objects[i];
        }
        List<Double> avg8h = O3Utils.get8Avg(o3Arr);
        Double maxAvg = O3Utils.get8MaxAvg(avg8h);
        result.put("03_8h_max",maxAvg);
        return result;
    }


    /**
     * @return AqiVO
     * @Author Jiatp
     * @Description //TODO  计算所对应污染物的信息
     * @Date 10:34 上午 2022/5/6
     * @Param type,value
     **/
    @Override
    public AqiVO getAqiMsg(String type, String value) throws CustomException {
        if (StrUtil.isBlank(type)) {
            throw new NullPointerException("污染物类型不能为空！");
        }
        if (StrUtil.isBlank(value)) {
            throw new NullPointerException("污染物浓度值不能为空！");
        }
        String name = AQITypeEnum.getName(type);
        if (AQITypeEnum.unKnow.getName().equals(name)) {
            throw new CustomException("污染物类型未知！");
        }
        AqiVO aQlTable = AqiUtils.getAQlTable(type, value);
        return aQlTable;
    }
}
