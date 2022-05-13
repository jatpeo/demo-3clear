package org.example.clear3.service;

import org.example.clear3.domain.param.BaseQueryParam;
import org.example.clear3.domain.param.DayAverageParam;
import org.example.clear3.domain.param.SingleTypeParam;
import org.example.clear3.domain.param.TimeCompareParam;
import org.example.clear3.exception.CustomException;

import java.util.List;
import java.util.Map;

/**
 * @program: demo-3clear
 * @description:
 * @author: Jiatp
 * @create: 2022-05-12 11:47
 **/
public interface TscPollutantcService {


    /**
     * @Description: //TODO 根据查询类型算出日均
     * @Author: Jiatp
     * @Date: 2022/5/12 1:02 下午
     * @param param
     * @return: java.util.List<java.util.Map>
     */
    List<Map> queryPollutionType(SingleTypeParam param) throws CustomException;

    /**
     * @Description: //TODO 时段对比分析查询
     * @Author: Jiatp
     * @Date: 2022/5/13 10:44 上午
     * @param param  查询参数
     * @return: java.util.List<java.util.Map>
     */
    List<Map> timeCompareAnalyzeQuery(TimeCompareParam param) throws Exception;

    /**
     * @Description: //TODO 基于小时查询多城市的日均值 （新）
     * @Author: Jiatp
     * @Date: 2022/5/13 5:23 下午
     * @param param
     * @return: java.util.List<java.util.Map>
     */
     List<Map> queryDayAverage(DayAverageParam param);
}
