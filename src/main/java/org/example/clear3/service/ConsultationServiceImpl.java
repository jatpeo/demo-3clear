package org.example.clear3.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.clear3.constant.PredictionIntervalContants;
import org.example.clear3.domain.TConsultationReport;
import org.example.clear3.domain.param.ConsultationReportParam;
import org.example.clear3.domain.param.DayAverageParam;
import org.example.clear3.domain.param.ManualEvaluationParam;
import org.example.clear3.enums.AQIEnum;
import org.example.clear3.mapper.TConsultationReportMapper;
import org.example.clear3.util.ComCalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    private MonitorService monitorService;


    /**
     * @param beginDataDate
     * @param endDataDate
     * @param cityCode
     * @param response
     * @Description: //TODO 人工预报导出
     * @Author: Jiatp
     * @Date: 2022/5/19 3:21 下午
     */
    @Override
    public void manualEvaluationExport(String beginDataDate, String endDataDate, String cityCode, HttpServletResponse response) throws IOException {

        ManualEvaluationParam manualEvaluationParam = new ManualEvaluationParam();
        manualEvaluationParam.setBeginDataDate(beginDataDate);
        manualEvaluationParam.setEndDataDate(endDataDate);
        manualEvaluationParam.setCityCode(cityCode);
        TemplateExportParams params = new TemplateExportParams("template/人工评估.xlsx");
        //查询结果
        Map<String, Object> map = this.manualEvaluation(manualEvaluationParam);
        //结果集合转换
        if (CollectionUtil.isNotEmpty(map)) {
            List<Map> list = (List<Map>) map.get("details");
            list.forEach(item -> {
                item.forEach((k, v) -> {
                    if ("ok".equals(v)) {
                        item.put(k, "√");
                    } else if ("no".equals(v)) {
                        item.put(k, "×");
                    }
                });
            });

        }
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        OutputStream outputStream = null;
        String filenamedisplay = URLEncoder.encode("人工评估报表" + System.currentTimeMillis(), "UTF-8") + ".xls";
        // 重置响应对象
        response.reset();
        // 指定下载的文件名--设置响应头
        response.setContentType("application/x-download;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + filenamedisplay);
        outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
        log.info("导出成功...");

    }

    /**
     * @param params 查询参数
     * @Description: //TODO 人工预报评估
     * @Author: Jiatp
     * @Date: 2022/5/18 4:36 下午
     * @return: java.util.List<java.util.Map>
     */
    @Override
    public Map<String, Object> manualEvaluation(ManualEvaluationParam params) {
        //1、根据起报时间计算实测日均值
        DayAverageParam dayAverageParam = new DayAverageParam();
        dayAverageParam.setBeginTimeStr(params.getBeginDataDate());
        dayAverageParam.setEndTimeStr(params.getEndDataDate());
        dayAverageParam.setCityCode(params.getCityCode());
        List<Map> maps = monitorService.queryDayAverage(dayAverageParam);
        if (CollectionUtil.isEmpty(maps)) {
            throw new NullPointerException("未查询到日均值！");
        }
        //2、根据起报时间计算人工填报的数据
        QueryWrapper<TConsultationReport> reportQueryWrapper = new QueryWrapper<>();
        reportQueryWrapper.eq("citycode", params.getCityCode());
        reportQueryWrapper.ge("datadate", Timestamp.valueOf(params.getBeginDataDate()));
        reportQueryWrapper.le("datadate", Timestamp.valueOf(params.getEndDataDate()));
        reportQueryWrapper.eq("predictioninterval", new BigDecimal(PredictionIntervalContants.INTERVAL_ONE));
        List<TConsultationReport> tConsultationReports = mapper.selectList(reportQueryWrapper);
        if (CollectionUtil.isEmpty(tConsultationReports)) {
            throw new NullPointerException("人工填报时段为空！");
        }
        //3、比对两个值构造数据
        log.info("开始比较...");
        //实测数据
        Map<String, List<Map>> resultMap = maps.stream().collect(Collectors.groupingBy(o -> {
            return o.get("monitorddate") + "," + o.get("citycode");
        }));
        //返回的总的结果集
        Map<String, Object> result = MapUtil.createMap(LinkedHashMap.class);
        //存放详情的集合
        List<Map> details = new ArrayList<>();
        tConsultationReports.forEach(item -> {
            String dateTime = DateUtil.formatDate(DateUtil.date(item.getDatadate()));
            String cityCode = item.getCitycode();
            List<Map> cMap = resultMap.get(dateTime + "," + cityCode);
            log.info(dateTime);
            Map<String, Object> map = compareValues(item, cMap);
            details.add(map);
        });
        result.put("details", details);
        //判断总的集合详情
        String[] valueAssess = {"aqi_level_assess", "aqi_assess", "primary_pollutant_assess", "pm25_1h_assess"};
        log.info("开始计算最后统计...");
        for (String type : valueAssess) {
            Map<String, List<Map>> mapList = details.stream().filter(map -> map.get(type) != null).collect(Collectors.groupingBy(o -> {
                return o.get(type).toString();
            }));
            totalComapre(type, mapList, result);
        }
        result.put("total_day", details.size());
        result.put("total_aqi_effective_day", details.size());
        result.put("total_primary_pollutant_effective_day", details.size());
        result.put("total_o3_effective_day", details.size());
        log.info("人工预报评估成功...");
        return result;
    }

    /**
     * @param type    类型
     * @param mapList 结果集合
     * @param result  总的返回集合
     * @Description: //TODO  计算最后合并值
     * @Author: Jiatp
     * @Date: 2022/5/19 11:52 上午
     */
    void totalComapre(String type, Map<String, List<Map>> mapList, Map<String, Object> result) {
        int ok = 0;
        int no = 0;
        double rate = 0.0;
        int highDay = 0;
        int lowDay = 0;
        if (mapList.size() > 1) {
            List<Map> okList = mapList.get("ok");
            List<Map> noList = mapList.get("no");
            ok = okList.size();
            no = noList.size();
            double total = ok + no;
            rate = (ok / total) * 100;
        } else {
            List<Map> okList = mapList.get("ok");
            List<Map> noList = mapList.get("no");
            if (CollectionUtil.isNotEmpty(okList)) {
                ok = okList.size();
                double total = ok + no;
                rate = (ok / total) * 100;
            } else if (CollectionUtil.isNotEmpty(noList)) {
                no = noList.size();
                double total = ok + no;
                rate = (ok / total) * 100;
            }
        }
        if (CollectionUtil.isNotEmpty(mapList.get("no"))) {
            List<Map> list = mapList.get("no");
            Map<Object, List<Map>> map = list.stream().collect(Collectors.groupingBy(o -> {
                return o.get(type + "_type");
            }));
            List<Map> lowList = map.get("low");
            List<Map> highList = map.get("high");
            if (CollectionUtil.isNotEmpty(lowList)) {
                lowDay = lowList.size();
            }
            if (CollectionUtil.isNotEmpty(highList)) {
                highDay = highList.size();
            }

        }
        result.put(type + "_effectiveDay", ok);
        result.put(type + "_invalidDay", no);
        result.put(type + "_effectiveRate", ComCalUtil.sciCal(rate, 2) + "%");
        result.put(type + "_highDay", highDay);
        result.put(type + "_lowDay", lowDay);
    }


    /**
     * @param consultationReport 人工填报数据
     * @param cMap               实测数据
     * @Description: //TODO  开始比较人工填报数据和实测数据进行比较
     * @Author: Jiatp
     * @Date: 2022/5/19 8:55 上午
     * @return: java.util.Map
     */
    private Map<String, Object> compareValues(TConsultationReport consultationReport, List<Map> cMap) {

        Map<String, Object> result = MapUtil.createMap(LinkedHashMap.class);
        Map<String, Object> pMap = MapUtil.createMap(LinkedHashMap.class);
        pMap.put("aqi", ComCalUtil.sciCal(consultationReport.getAqiMin().doubleValue(), 0) + "~"
                + ComCalUtil.sciCal(consultationReport.getAqiMax().doubleValue(), 0));
        pMap.put("primary_pollutant", consultationReport.getPrimaryPollutant());
        pMap.put("pm25_1h", ComCalUtil.sciCal(consultationReport.getPm25Min().doubleValue(), 0) + "~"
                + ComCalUtil.sciCal(consultationReport.getPm25Max().doubleValue(), 0));
        pMap.put("o3_8h", ComCalUtil.sciCal(consultationReport.getO38hMin().doubleValue(), 0) + "~"
                + ComCalUtil.sciCal(consultationReport.getO38hMax().doubleValue(), 0));
        pMap.put("aqi_level", consultationReport.getAqiMinType() + "~" + consultationReport.getAqiMaxType());
        String citCode = null;
        String cityName = null;
        Date monitordDate = null;
        //结果集合
        Map<String, Object> map = cMap.get(0);
        for (String item : PredictionIntervalContants.BASE_PREDICTION_ITEM) {
            String obj = map.get(item).toString();
            citCode = map.get("citycode").toString();
            cityName = map.get("cityname").toString();
            monitordDate = DateUtil.parse(map.get("monitorddate").toString());
            //去判断每一项
            valueAssessed(item, pMap, obj, result);
        }
        result.put("starting_time", DateUtil.formatDate(DateUtil.offset(monitordDate, DateField.DAY_OF_MONTH, -1)));
        result.put("prediction_time", DateUtil.formatDate(monitordDate));
        result.put("cityname", cityName);
        result.put("citycode", citCode);

        return result;
    }

    /**
     * @param pMap   人工填报类型
     * @param item   指标类型
     * @param obj    实测结果
     * @param result 结果集合
     * @Description: //TODO 开始比较人工填报数据和实测数据进行比较（比较每一项的值填充内容）
     * @Author: Jiatp
     * @Date: 2022/5/19 10:51 上午
     * @return: java.lang.String
     */
    private void valueAssessed(String item, Map<String, Object> pMap, String obj, Map<String, Object> result) {
        result.put(item + "_prediction", pMap.get(item).toString());
        result.put(item + "_result", obj);
        if (!"primary_pollutant".equals(item) && !"aqi_level".equals(item)) {
            String[] val = pMap.get(item).toString().split("~");
            if (Double.parseDouble(obj) >= Double.parseDouble(val[0]) && Double.parseDouble(obj) <= Double.parseDouble(val[1])) {
                result.put(item + "_assess", "ok");
            } else {
                result.put(item + "_assess", "no");
                if (Double.parseDouble(obj) < Double.parseDouble(val[0])) {
                    result.put(item + "_assess_type", "low");
                }
                if (Double.parseDouble(obj) > Double.parseDouble(val[1])) {
                    result.put(item + "_assess_type", "high");
                }
            }
        } else {
            result.put(item + "_assess", pMap.get(item).toString().contains(obj) ? "ok" : "no");
        }
    }


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

        for (ConsultationReportParam param : params) {
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