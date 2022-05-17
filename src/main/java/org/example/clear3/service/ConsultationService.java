package org.example.clear3.service;

import org.example.clear3.domain.param.ConsultationReportParam;

import java.util.List;

/**
 * @program: demo-3clear
 * @description: 预报会商
 * @author: Jiatp
 * @create: 2022-05-17 11:19
 **/
public interface ConsultationService {

    /**
     * @param params 查询参数
     * @Description: //TODO
     * @Author: Jiatp
     * @Date: 2022/5/17 11:29 上午
     * @return: java.util.List<java.util.Map>
     */
    void manualReporting(List<ConsultationReportParam> params);
}
