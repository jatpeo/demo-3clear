package org.example.clear3.service;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.clear3.domain.TConsultationReport;
import org.example.clear3.domain.param.ConsultationReportParam;
import org.example.clear3.enums.AQIEnum;
import org.example.clear3.mapper.TConsultationReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: demo-3clear
 * @description: 预报会商实现类
 * @author: Jiatp
 * @create: 2022-05-17 11:20
 **/
@Slf4j
@Service
public class ConsultationServiceImpl implements ConsultationService {


    @Autowired
    private TConsultationReportMapper mapper;


    /**
     * @param params 人工上报数据
     * @Description: //TODO
     * @Author: Jiatp
     * @Date: 2022/5/17 11:29 上午
     * @return: java.util.List<java.util.Map>
     */
    @Override
    public void manualReporting(List<ConsultationReportParam> params) {

        List<TConsultationReport> list = new ArrayList<>();

        for(ConsultationReportParam param:params){
            TConsultationReport tConsultationReport = new TConsultationReport();
            tConsultationReport.setDatadate(Timestamp.valueOf(param.getDatadate()));
            tConsultationReport.setPredictiontime(Timestamp.valueOf(param.getPredictiontime()));
            tConsultationReport.setCitycode("620100");
            tConsultationReport.setUserCode("3clearTest");
            tConsultationReport.setAqiMax(new BigDecimal(param.getAqiMax()));
            tConsultationReport.setAqiMin(new BigDecimal(param.getAqiMin()));
            //调用通用接口判断aqi然后给赋值
            String[] aqiMaxMsg = AQIEnum.getName(Double.parseDouble(param.getAqiMax())).split(",");
            String[] aqiMinMsg = AQIEnum.getName(Double.parseDouble(param.getAqiMin())).split(",");
            tConsultationReport.setAqiMaxColor(aqiMaxMsg[2]);
            tConsultationReport.setAqiMinColor(aqiMinMsg[2]);
            tConsultationReport.setAqiMaxLevel(aqiMaxMsg[3]);
            tConsultationReport.setAqiMinLevel(aqiMinMsg[3]);
            tConsultationReport.setAqiMaxType(aqiMaxMsg[0]);
            tConsultationReport.setAqiMinType(aqiMinMsg[0]);
            tConsultationReport.setO38hMax(new BigDecimal(param.getO38hMax()));
            tConsultationReport.setO38hMin(new BigDecimal(param.getO38hMin()));
            tConsultationReport.setPm25Max(new BigDecimal(param.getPm25Max()));
            tConsultationReport.setPm25Min(new BigDecimal(param.getAqiMin()));
            //计算时间差
            long between = DateUtil.between(DateUtil.parse(param.getDatadate()), DateUtil.parse(param.getPredictiontime()), DateUnit.DAY);
            tConsultationReport.setPredictioninterval((double) between);
            tConsultationReport.setPrimaryPollutant(param.getPrimaryPollutant());
            tConsultationReport.setCreateTime(new Timestamp(System.currentTimeMillis()));
            list.add(tConsultationReport);
        }
        mapper.insertBatchData(list);
        log.info("保存成功！");


    }
}