package org.example.clear3.service;

import org.example.clear3.domain.param.CityPredictionTimeParam;

import java.util.List;
import java.util.Map;

/**
 * @program: demo-3clear
 * @description: 预报分析Service
 * @author: Jiatp
 * @create: 2022-05-16 10:42
 **/
public interface PredictionService {
    /**
     * @Description: //TODO 城市逐日预报controller
     * @Author: Jiatp
     * @Date: 2022/5/16 10:46 上午
     * @param params 查询参数
     * @return: java.util.List<java.util.Map>
     */
    List<Map> cityPredictionByTimeQuery(CityPredictionTimeParam params);
}
