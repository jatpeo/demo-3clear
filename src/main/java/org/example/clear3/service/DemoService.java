package org.example.clear3.service;

import org.example.clear3.domain.vo.AQIVo;
import org.example.clear3.exception.CustomException;

public interface DemoService {

    /**
     * @return
     * @Author Jiatp  10:34 上午 2022/5/6
     * @Description //TODO 得到污染对应的信息
     * @Param
     **/
    AQIVo getAqiMsg(String type, String value) throws CustomException;
}
