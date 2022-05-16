package org.example.clear3.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.clear3.domain.param.CityPredictionTimeParam;
import org.example.clear3.service.PredictionService;
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
 * @program: demo-3clear
 * @description: 预报分析[controller]
 * @author: Jiatp
 * @create: 2022-05-16 10:40
 **/
@Slf4j
@RestController
@RequestMapping("/predict")
public class PredictionController {

    /**
     * 是否为开发模式
     */
    @Value("${3clearServer.dev}")
    private boolean isDev;

    @Autowired
    private PredictionService predictionService;


    /**
     * @Description: //TODO 城市逐日预报controller
     * @Author: Jiatp
     * @Date: 2022/5/16 10:46 上午
     * @param params 查询参数
     * @return: org.example.clear3.util.RespBean
     */
    @GetMapping("/cityPredictionByTimeQuery")
    public RespBean cityPredictionByTimeQuery(@Valid @RequestBody CityPredictionTimeParam params) {
        try {
            List<Map> lists = predictionService.cityPredictionByTimeQuery(params);
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
