package org.example.clear3.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.clear3.domain.param.DayAverageParam;
import org.example.clear3.domain.param.SingleTypeParam;
import org.example.clear3.domain.param.TimeCompareParam;
import org.example.clear3.service.MonitorService;
import org.example.clear3.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author Jiatp
 * @Description //TODO 实测功能模块下
 * @Date 7:28 下午 2022/5/6
 **/
@Slf4j
@RestController
@RequestMapping("/tscPh")
public class MonitorController {

    /**
     * 是否为开发模式
     */
    @Value("${3clearServer.dev}")
    private boolean isDev;
    @Autowired
    MonitorService pollutantcService;


    /**
     * @Description: //TODO 时段对比分析查询
     * @Author: Jiatp
     * @Date: 2022/5/16 4:02 下午
     * @param param 查询参数
     * @return: org.example.clear3.util.RespBean
     */
    @GetMapping("/timeCompareAnalyzeQuery")
    public RespBean timeCompareAnalyzeQuery(@Valid @RequestBody TimeCompareParam param) {
        try {
            List<Map> lists = pollutantcService.timeCompareAnalyzeQuery(param);
            return RespBean.ok("查询成功",lists);
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
     * @Description: //TODO 计算多城市单个指标的浓度值和iaqi
     * @Author: Jiatp
     * @Date: 2022/5/12 7:40 下午
     * @param param 查询参数
     * @return: org.example.clear3.util.RespBean
     */
    @GetMapping("/queryPollutionType")
    public RespBean queryPollutionType(@Valid @RequestBody SingleTypeParam param) {
        try {
            List<Map> lists = pollutantcService.queryPollutionType(param);
            return RespBean.ok("查询成功",lists);
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
     * @Description: //TODO 查询多城市的日均值 （新）
     * @Author: Jiatp
     * @Date: 2022/5/13 5:21 下午
     * @param param 查询参数
     * @return: org.example.clear3.util.RespBean
     */
    @GetMapping("/queryDayAverage")
    public RespBean queryDayAverage(@Valid @RequestBody DayAverageParam param) {
        try {
            List<Map> lists = pollutantcService.queryDayAverage(param);
            return RespBean.ok("查询成功",lists);
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
