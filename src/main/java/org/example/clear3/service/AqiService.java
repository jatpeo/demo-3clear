package org.example.clear3.service;

import org.example.clear3.domain.vo.AqiVO;
import org.example.clear3.exception.CustomException;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @Author Jiatp
 * @Description //TODO aqi service
 * @Date 5:57 下午 2022/5/7
 **/
public interface AqiService {

    /**
     * @return AqiVO
     * @Author Jiatp  10:34 上午 2022/5/6
     * @Description //TODO 得到污染对应的信息
     * @Param type,value
     **/
    AqiVO getAqiMsg(String type, String value) throws CustomException;

    /**
     * @return List<Map<String, Object>>
     * @Author Jiatp
     * @Description //TODO  计算城市日均污染指标
     * @Date 3:29 下午 2022/5/7
     * @Param time,city
     **/
    List<Map<String, Object>> getAqiAvg(String time, String city) throws CustomException, ParseException;
}
