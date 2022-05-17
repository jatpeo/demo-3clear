package org.example.clear3.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.clear3.domain.param.CityPredictionTimeParam;
import org.example.clear3.domain.vo.CityPredictionTimeVO;
import org.example.clear3.mapper.TscPollutantcHourMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: demo-3clear
 * @description: 预报分析下-serviceimpl
 * @author: Jiatp
 * @create: 2022-05-16 10:42
 **/
@Slf4j
@Service
public class PredictionServiceImpl implements PredictionService {


    @Autowired
    private TscPollutantcHourMapper mapper;

    /**
     * @param params 查询参数
     * @Description: //TODO 城市逐日预报查询
     * @Author: Jiatp
     * @Date: 2022/5/16 11:12 上午
     * @return: java.util.List<java.util.Map>
     */
    @Override
    public List<Map> cityPredictionByTimeQuery(CityPredictionTimeParam params) {

        //1 处理动态表
        CityPredictionTimeVO cityPredictionTimeVO = new CityPredictionTimeVO();
        cityPredictionTimeVO.setTableName("t_" + params.getModelName() + "_" + params.getZoneName() + "_c_" + params.getDateType());
        cityPredictionTimeVO.setCityCode(params.getCityCode());
        cityPredictionTimeVO.setPredictionTime(params.getPredictionTime());
        cityPredictionTimeVO.setTarget(params.getTarget());
        //2 算出起报时间的长度
        String[] time = params.getPredictionTime().split(" ");
        Date predictionDate = DateUtil.parse(time[0], "yyyy-MM-dd");
        Map<String, Object> map = transPredictTime(params.getPredictionLong(), params.getDateType(), predictionDate);
        cityPredictionTimeVO.setStartDataDate(map.get("beginStr").toString());
        cityPredictionTimeVO.setEndDataDate(map.get("endStr").toString());
        List<Map> result = mapper.cityPredictionByTimeQuery(cityPredictionTimeVO);
        log.info(JSONUtil.toJsonStr(result));
        return result;
    }

    /**
     * @param predictionLong 预报时长
     * @param dateType       预报类型
     * @param predictionDate 预报日期
     * @Description: //TODO 转换预测时间
     * @Author: Jiatp
     * @Date: 2022/5/16 2:31 下午
     */
    private Map<String, Object> transPredictTime(String predictionLong, String dateType, Date predictionDate) {
        Map<String, Object> map = MapUtil.createMap(HashMap.class);
        Date beginDate = DateUtil.offset(predictionDate, DateField.DAY_OF_MONTH, 1);
        Date endDate = DateUtil.offset(predictionDate, DateField.DAY_OF_MONTH, Integer.parseInt(predictionLong) + 1);
        String beginStr = DateUtil.formatDateTime(beginDate);
        String endStr = DateUtil.formatDateTime(endDate);
        map.put("beginStr", beginStr);
        map.put("endStr", endStr);
        return map;
    }
}
