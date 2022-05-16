package org.example.clear3.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.clear3.domain.vo.AqiVO;
import org.example.clear3.service.AqiService;
import org.example.clear3.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author Jiatp
 * @Description //TODO 演示预报预警系统的一些通用controller
 * @Date 9:59 上午 2022/5/6
 **/
@Slf4j
@RestController
@RequestMapping("/aqi")
public class AqiController {

    /**
     * 是否为开发模式
     */
    @Value("${3clearServer.dev}")
    private boolean isDev;

    @Autowired
    private AqiService aqiService;

   /**
    * @Description: //TODO 导出城市日均变化值
    * @Author: Jiatp
    * @Date: 2022/5/16 4:45 下午
    * @param beginTimeStr 开始时间
    * @param endTimeStr   结束时间
    * @param cityCode     城市编码
    * @param fileName     文件名称
    * @param response     响应名称
    * @return: org.example.clear3.util.RespBean
    */
    @GetMapping("/exportCityAvg")
    public RespBean exportCityAvg(@RequestParam("beginTimeStr") String beginTimeStr,
                                  @RequestParam("endTimeStr") String endTimeStr,
                                  @RequestParam("cityCode") String cityCode,
                                  @RequestParam("fileName") String fileName,
                                  HttpServletResponse response) {
        try {
            aqiService.exportCityAvg(beginTimeStr, endTimeStr, cityCode, fileName, response);
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
     * @param beginTimeStr 开始时间
     * @param endTimeStr   结束时间
     * @param cityCode     城市编码
     * @Description: //TODO 计算城市日均增长
     * @Author: Jiatp
     * @Date: 2022/5/9 9:33 上午
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
     * @param beginTimeStr 开始时间
     * @param endTimeStr   结束时间
     * @param cityCode     城市编码
     * @Description: //TODO 多城市计算日均aqi（旧）
     * @Author: Jiatp
     * @Date: 2022/5/12 8:42 上午
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
     * @param type  指标
     * @param value 数值
     * @Description: //TODO 计算aqi信息
     * @Author: Jiatp
     * @Date: 2022/5/12 8:43 上午
     * @return: org.example.clear3.util.RespBean
     */
    @GetMapping("/getAqiMsg")
    public RespBean getAqiMsg(@RequestParam("type") String type, @RequestParam("value") String value) {
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
