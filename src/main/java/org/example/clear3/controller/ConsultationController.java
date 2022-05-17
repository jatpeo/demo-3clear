package org.example.clear3.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.clear3.domain.param.ConsultationReportParam;
import org.example.clear3.service.ConsultationService;
import org.example.clear3.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/** 预报会商
 * @program: demo-3clear
 * @description: 预报会商
 * @author: Jiatp
 * @create: 2022-05-17 11:14
 **/
@Slf4j
@RestController
@RequestMapping("/consultate")
public class ConsultationController {

    /**
     * 是否为开发模式
     */
    @Value("${3clearServer.dev}")
    private boolean isDev;

    @Autowired
    private ConsultationService consultationService;

    /** 人工上报数据
     * @Description: //TODO 人工上报数据
     * @Author: Jiatp
     * @Date: 2022/5/17 11:16 上午
     * @param params
     * @return: org.example.clear3.util.RespBean
     */
    @GetMapping("/manualReporting")
    public RespBean manualReporting(@Valid @RequestBody List<ConsultationReportParam> params) {
        try {
            consultationService.manualReporting(params);
            return RespBean.ok("上报成功");
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
