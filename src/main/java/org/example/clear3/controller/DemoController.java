package org.example.clear3.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.clear3.domain.vo.AQIVo;
import org.example.clear3.service.DemoService;
import org.example.clear3.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Jiatp
 * @Description //TODO 演示预报预警系统的一些通用controller
 * @Date 9:59 上午 2022/5/6
 **/
@Slf4j
@RestController
public class DemoController {
    /**
     * 是否为开发模式
     */
    @Value("${3clearServer.dev}")
    private boolean isDev;

    @Autowired
    private DemoService demoService;


    /**
     * @return
     * @Author Jiatp  10:16 上午 2022/5/6
     * @Description //TODO
     * @Param
     **/
    @GetMapping("/getAqiMsg")
    public RespBean getAqiMsg(@RequestParam("type") String type, @RequestParam("value") String value) {
        try {
            AQIVo vo = demoService.getAqiMsg(type,value);
            return RespBean.ok(type,vo);
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
     * @return
     * @Author Jiatp  9:59 上午 2022/5/6
     * @Description //TODO
     * @Param
     **/
    @GetMapping("/test")
    public RespBean test() {
        try {
            return RespBean.ok("你好！");
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
