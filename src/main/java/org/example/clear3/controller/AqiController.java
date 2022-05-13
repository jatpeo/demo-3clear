package org.example.clear3.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.clear3.domain.vo.AqiVO;
import org.example.clear3.service.AqiService;
import org.example.clear3.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author Jiatp
 * @Description //TODO 演示预报预警系统的一些通用controller
 * @Date 9:59 上午 2022/5/6
 **/
@Slf4j
@RestController
public class AqiController {

    /**
     * 是否为开发模式
     */
    @Value("${3clearServer.dev}")
    private boolean isDev;

    @Autowired
    private AqiService aqiService;

    /**
     * @param beginTimeStr
     * @param endTimeStr
     * @param cityCode
     * @param fileName
     * @Description: //TODO 导出城市日均
     * @Author: Jiatp
     * @Date: 2022/5/10 4:27 下午
     * @return: org.example.clear3.util.RespBean
     */
    @GetMapping("/exportCityAvg")
    public RespBean exportCityAvg(@RequestParam("beginTimeStr") String beginTimeStr,
                                  @RequestParam("endTimeStr") String endTimeStr,
                                  @RequestParam("cityCode") String cityCode,
                                  @RequestParam("fileName") String fileName) {
        try {
            aqiService.exportCityAvg(beginTimeStr, endTimeStr, cityCode,fileName);
            return RespBean.ok("导出成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            String errorMessage = "操作异常...";
            if (isDev) {
                errorMessage = e.getMessage();
            }
            return RespBean.error(errorMessage);
        }
    }

    /**
     * @Description: //TODO 计算城市日均增长
     * @Author: Jiatp
     * @Date: 2022/5/9 9:33 上午
     * @param beginTimeStr
     * @param endTimeStr
     * @param cityCode
     * @return: org.example.clear3.util.RespBean
     */
    @GetMapping("/getCityRateQuery")
    public RespBean getCityRateQuery(@RequestParam("beginTimeStr") String beginTimeStr,
                                     @RequestParam("endTimeStr") String endTimeStr,
                                     @RequestParam("cityCode") String cityCode) {
        try {
            List<Map<String, Object>> result = aqiService.getCityRateQuery(beginTimeStr, endTimeStr, cityCode);
            return RespBean.ok("查询成功", result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            String errorMessage = "操作异常...";
            if (isDev) {
                errorMessage = e.getMessage();
            }
            return RespBean.error(errorMessage);
        }
    }

    /**
     * @Description: //TODO 计算日均aqi
     * @Author: Jiatp
     * @Date: 2022/5/12 8:42 上午
     * @param beginTimeStr
     * @param endTimeStr
     * @param cityCode
     * @return: org.example.clear3.util.RespBean
     */
    @GetMapping("/getAqiDayAvg")
    public RespBean getAqiAvg(@RequestParam("beginTimeStr") String beginTimeStr,
                              @RequestParam("endTimeStr") String endTimeStr,
                              @RequestParam("cityCode") String cityCode) {
        try {
            List<Map<String, Object>> result = aqiService.getAqiAvg(beginTimeStr, endTimeStr, cityCode);
            return RespBean.ok("查询成功", result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            String errorMessage = "操作异常...";
            if (isDev) {
                errorMessage = e.getMessage();
            }
            return RespBean.error(errorMessage);
        }
    }


   /**
    * @Description: //TODO 计算aqi信息
    * @Author: Jiatp
    * @Date: 2022/5/12 8:43 上午
    * @param type
    * @param value
    * @return: org.example.clear3.util.RespBean
    */
    @GetMapping("/getAqiMsg")
    public RespBean getAqiMsg(@RequestParam("type") String type,
                              @RequestParam("value") String value) {
        try {
            AqiVO vo = aqiService.getAqiMsg(type, value);
            return RespBean.ok(type, vo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            String errorMessage = "操作异常...";
            if (isDev) {
                errorMessage = e.getMessage();
            }
            return RespBean.error(errorMessage);
        }
    }

}
