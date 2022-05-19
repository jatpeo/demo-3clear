package org.example.clear3.service;

import org.example.clear3.domain.param.ConsultationReportParam;
import org.example.clear3.domain.param.ManualEvaluationParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @program: demo-3clear
 * @description: 预报会商
 * @author: Jiatp
 * @create: 2022-05-17 11:19
 **/
public interface ConsultationService {
    /**
     * @param params 查询参数
     * @Description: //TODO 人工上报数据
     * @Author: Jiatp
     * @Date: 2022/5/17 11:29 上午
     * @return: java.util.List<java.util.Map>
     */
    void manualReporting(List<ConsultationReportParam> params);

    /**
     * @param params 查询参数
     * @Description: //TODO 人工预报评估
     * @Author: Jiatp
     * @Date: 2022/5/18 9:45 上午
     */
    Map<String, Object> manualEvaluation(ManualEvaluationParam params);

    /**
     * @param beginDataDate
     * @param endDataDate
     * @param cityCode
     * @param response
     * @Description: //TODO 人工预报导出
     * @Author: Jiatp
     * @Date: 2022/5/19 2:56 下午
     */
    void manualEvaluationExport(String beginDataDate, String endDataDate, String cityCode, HttpServletResponse response) throws IOException;
}
