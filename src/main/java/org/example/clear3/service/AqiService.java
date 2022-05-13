package org.example.clear3.service;

import org.example.clear3.domain.vo.AqiVO;
import org.example.clear3.exception.CustomException;

import java.io.IOException;
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
     * @Description: //TODO计算城市趋势
     * @Author: Jiatp
     * @Date: 2022/5/12 8:44 上午
     * @param beginTimeStr
     * @param endTimeStr
     * @param cityCode
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getCityRateQuery(String beginTimeStr, String endTimeStr, String cityCode) throws CustomException, ParseException;



    /**
     * @Description: //TODO 得到污染对应的信息
     * @Author: Jiatp
     * @Date: 2022/5/12 8:45 上午
     * @param type
     * @param value
     * @return: org.example.clear3.domain.vo.AqiVO
     */
    AqiVO getAqiMsg(String type, String value) throws CustomException;

    /**
     * @Description: //TODO 计算城市日均污染指标
     * @Author: Jiatp
     * @Date: 2022/5/12 8:45 上午
     * @param beginTimeStr
     * @param endTimeStr
     * @param citycode
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */

    List<Map<String, Object>> getAqiAvg(String beginTimeStr, String endTimeStr, String citycode) throws CustomException, ParseException;

    /**
     * @Description: //TODO 导出城市日均增长趋势
     * @Author: Jiatp
     * @Date: 2022/5/12 8:45 上午
     * @param beginTimeStr
     * @param endTimeStr
     * @param cityCode
     * @param fileName
     */
    void exportCityAvg(String beginTimeStr, String endTimeStr, String cityCode,String fileName) throws CustomException, ParseException, IOException;
}
