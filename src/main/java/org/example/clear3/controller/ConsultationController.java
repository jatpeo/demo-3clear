package org.example.clear3.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.clear3.domain.param.ConsultationReportParam;
import org.example.clear3.domain.param.ManualEvaluationParam;
import org.example.clear3.service.ConsultationService;
import org.example.clear3.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 预报会商
 *
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

    /**
     * @param beginDataDate
     * @param endDataDate
     * @param cityCode
     * @param response
     * @Description: //TODO 人工预报导出
     * @Author: Jiatp
     * @Date: 2022/5/19 2:58 下午
     * @return: org.example.clear3.util.RespBean
     */

    @GetMapping("/manualEvaluationExport")
    public RespBean manualEvaluationExport(@RequestParam("beginDataDate") String beginDataDate,
                                           @RequestParam("endDataDate") String endDataDate,
                                           @RequestParam("cityCode") String cityCode,
                                           HttpServletResponse response) {
        try {
            consultationService.manualEvaluationExport(beginDataDate, endDataDate, cityCode, response);
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
     * @param params 查询参数
     * @Description: //TODO 人工预报评估
     * @Author: Jiatp
     * @Date: 2022/5/18 9:41 上午
     * @return: org.example.clear3.util.RespBean
     */
    @GetMapping("/manualEvaluation")
    public RespBean manualEvaluation(@Valid @RequestBody ManualEvaluationParam params) {
        try {
            Map<String, Object> maps = consultationService.manualEvaluation(params);
            return RespBean.ok("评估成功", maps);
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
     * 人工上报数据
     *
     * @param params
     * @Description: //TODO 人工上报数据
     * @Author: Jiatp
     * @Date: 2022/5/17 11:16 上午
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
