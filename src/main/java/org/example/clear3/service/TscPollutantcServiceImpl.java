package org.example.clear3.service;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.clear3.domain.param.BaseQueryParam;
import org.example.clear3.domain.param.DayAverageParam;
import org.example.clear3.domain.param.SingleTypeParam;
import org.example.clear3.domain.param.TimeCompareParam;
import org.example.clear3.exception.CustomException;
import org.example.clear3.mapper.TscPollutantcHourMapper;
import org.example.clear3.util.ComCalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: demo-3clear
 * @description: //TODO service
 * @author: Jiatp
 * @create: 2022-05-12 11:48
 **/
@Service
@Slf4j
public class TscPollutantcServiceImpl implements TscPollutantcService {


    @Autowired
    TscPollutantcHourMapper pollutantcHourMapper;

    /**
     * @param params 查询参数
     * @Description: //TODO 指标时段对比分析查询浓度和iaqi
     * @Author: Jiatp
     * @Date: 2022/5/13 10:45 上午
     * @return: java.util.List<java.util.Map>
     */
    @Override
    public List<Map> timeCompareAnalyzeQuery(TimeCompareParam params) throws Exception {
        //select
        List<Map> groupList = pollutantcHourMapper.timeCompareQuery(params);
        //将空的重新组合
        List<Map> totalList = handleEmptyTimeList(groupList);
        //结果集
        List<Map> result = new ArrayList<>();
        //按照小时进行分组
        Map<String, List<Map>> resultMap = totalList.stream().collect(Collectors.groupingBy(o -> {
            return o.get("monitordate").toString().split(" ")[1] + "," + o.get("citycode").toString() + "," + o.get("cityname").toString();
        }));
        log.info(JSONUtil.toJsonStr(resultMap));
        //循环list
        resultMap.forEach((key, value) -> {
            //k->time
            String time = key.split(",")[0];
            String cityCode = key.split(",")[1];
            String cityName = key.split(",")[2];
            String monitorHour = time.substring(0, 2);
            //计算
            try {
                Map<String, Object> cityTimeValue = ComCalUtil.getCityTimeValue(value, params.getTarget(), monitorHour, cityCode, cityName);
                result.add(cityTimeValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return result;
    }

    /**
     * @param groupList
     * @Description: //TODO 处理时段对比分析空的值
     * @Author: Jiatp
     * @Date: 2022/5/13 4:56 下午
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     */

    public List<Map> handleEmptyTimeList(List<Map> groupList) {

        for (int i = 0; i < groupList.size(); i++) {
            Map value = groupList.get(i);
            String monitorDate = value.get("monitordate").toString();
            if ("-999".equals(monitorDate)) {
                value.remove("monitordate");
                value.put("monitordate", "0000-00-00 00:00:00");
                //工作
                for (int j = 1; j <= 23; j++) {
                    Map<Object, Object> map = MapUtil.createMap(HashMap.class);
                    map.putAll(value);
                    map.remove("monitordate");
                    String time = j >= 10 ? "0000-00-00 " + j + ":00:00" : "0000-00-00 0" + j + ":00:00";
                    map.put("monitordate", time);
                    groupList.add(map);
                }
            }
        }
        return groupList;
    }


    /**
     * @param params
     * @Description: //TODO 计算单个指标的浓度和iaqi
     * @Author: Jiatp
     * @Date: 2022/5/12 4:17 下午
     * @return: java.util.List<java.util.Map>
     */
    @Override
    public List<Map> queryPollutionType(SingleTypeParam params) {
        List<Map> mapList = pollutantcHourMapper.queryPollutionType(params);
        //按天进行分组
        Map<String, List<Map>> resultMap = mapList.stream().collect(Collectors.groupingBy(o -> {
            return o.get("monitordate").toString().split(" ")[0] + "," + o.get("citycode").toString();
        }));
        log.info(JSONUtil.toJsonStr(resultMap.size()));
        List<Map> list = new ArrayList<>();
        resultMap.forEach((k, v) -> {
            Map<String, Object> objectMap = MapUtil.createMap(LinkedHashMap.class);
            String date = k.split(",")[0];
            String city = k.split(",")[1];
            Map<String, Object> cityDayAvg = null;
            try {
                cityDayAvg = ComCalUtil.getCityDayAvg(v, params.getTarget());
            } catch (CustomException e) {
                e.printStackTrace();
            }
            objectMap.put("citycode", city);
            objectMap.put("monitordate", date);
            if ("o3_1h".equals(params.getTarget())) {
                objectMap.put(params.getTarget() + "_max", cityDayAvg.get(params.getTarget() + "_max"));
            } else {
                objectMap.put(params.getTarget(), cityDayAvg.get(params.getTarget()));
            }
            objectMap.put(params.getTarget() + "_iaqi", cityDayAvg.get(params.getTarget() + "_iaqi"));
            list.add(objectMap);

        });
        return list;
    }


    /**
     * @param params
     * @Description: //TODO 基于小时查询多城市的日均值 （新）
     * @Author: Jiatp
     * @Date: 2022/5/13 5:52 下午
     * @return: java.util.List<java.util.Map>
     */
    @Override
    public List<Map> queryDayAverage(DayAverageParam params) {

        List<Map> resultMap = pollutantcHourMapper.queryDayAverage(params);

        log.info(JSONUtil.toJsonStr(resultMap.size()));

        return null;
    }
}
