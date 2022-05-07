package org.example.clear3.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.clear3.domain.vo.AqiVO;
import org.example.clear3.service.AqiService;
import org.example.clear3.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
    private AqiService demoService;


    /**
     * @return RespBean
     * @Author Jiatp
     * @Description //TODO
     * @Date 10:01 上午 2022/5/7
     * @Param time,city
     **/
    @GetMapping("/getAqiDayAvg")
    public RespBean getAqiAvg(@RequestParam("time") String time,
                              @RequestParam("city") String city) {
        try {
            List<Map<String, Object>> result = demoService.getAqiAvg(time, city);
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
     * @return RespBean
     * @Author Jiatp  10:16 上午 2022/5/6
     * @Description //TODO
     * @Param type,value
     **/
    @GetMapping("/getAqiMsg")
    public RespBean getAqiMsg(@RequestParam("type") String type,
                              @RequestParam("value") String value) {
        try {
            AqiVO vo = demoService.getAqiMsg(type, value);
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
