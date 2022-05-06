package org.example.clear3.service;

import org.example.clear3.domain.vo.AQIVo;

public interface DemoService {

    /**
     * @Author Jiatp  10:34 上午 2022/5/6
     * @Description //TODO 得到污染对应的信息
     * @Param
     * @return
     **/
    AQIVo getAqiMsg(String type, String value);
}
