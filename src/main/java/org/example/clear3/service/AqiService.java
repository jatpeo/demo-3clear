package org.example.clear3.service;

import org.example.clear3.domain.vo.AqiVO;
import org.example.clear3.exception.CustomException;

import javax.servlet.http.HttpServletResponse;
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
     * @param beginTimeStr 开始时间
     * @param endTimeStr   结束时间
     * @param cityCode     城市编码
     * @Description: //TODO计算城市趋势
     * @Author: Jiatp
     * @Date: 2022/5/12 8:44 上午
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    List<Map<String, Object>> getCityRateQuery(String beginTimeStr, String endTimeStr, String cityCode) throws CustomException, ParseException;


    /**
     * @param type  指标类型
     * @param value 指标值
     * @Description: //TODO 得到污染对应的信息
     * @Author: Jiatp
     * @Date: 2022/5/12 8:45 上午
     * @return: org.example.clear3.domain.vo.AqiVO
     */
    AqiVO getAqiMsg(String type, String value) throws CustomException;

    /**
     * @param beginTimeStr 开始时间
     * @param endTimeStr   结束时间
     * @param citycode     城市编码
     * @Description: //TODO 计算城市日均污染指标
     * @Author: Jiatp
     * @Date: 2022/5/12 8:45 上午
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    List<Map<String, Object>> getAqiAvg(String beginTimeStr, String endTimeStr, String citycode) throws CustomException, ParseException;

    /**
     * @param beginTimeStr 开始时间
     * @param endTimeStr   结束时间
     * @param cityCode     城市编码
     * @param fileName     文件名称
     * @param response     响应对象
     * @Description: //TODO 导出城市日均增长趋势
     * @Author: Jiatp
     * @Date: 2022/5/16 4:44 下午
     */
    void exportCityAvg(String beginTimeStr, String endTimeStr, String cityCode, String fileName, HttpServletResponse response) throws CustomException, ParseException, IOException;
}
