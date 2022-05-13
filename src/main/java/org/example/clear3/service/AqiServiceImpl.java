package org.example.clear3.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.example.clear3.domain.TscPollutantcHour;
import org.example.clear3.domain.vo.AqiVO;
import org.example.clear3.enums.AQITypeEnum;
import org.example.clear3.exception.CustomException;
import org.example.clear3.mapper.TscPollutantcHourMapper;
import org.example.clear3.util.AqiUtils;
import org.example.clear3.util.DateUtils;
import org.example.clear3.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author Jiatp
 * @Description //TODO aqi计算类
 * @Date 5:46 下午 2022/5/7
 **/
@Service
@Slf4j
public class AqiServiceImpl implements AqiService {


    @Autowired
    TscPollutantcHourMapper tScPollutantcHourMapper;

    /**
     * @param beginTimeStr
     * @param endTimeStr
     * @param cityCode
     * @Description: //TODO 导出城市变化趋势
     * @Author: Jiatp
     * @Date: 2022/5/10 4:28 下午
     */
    @Override
    public void exportCityAvg(String beginTimeStr, String endTimeStr, String cityCode, String fileName) throws CustomException, ParseException, IOException {

        if (StrUtil.isEmpty(beginTimeStr)) {
            throw new CustomException("开始时间不能为空");
        }
        if (StrUtil.isEmpty(endTimeStr)) {
            throw new CustomException("结束时间不能为空");
        }
        if (StrUtil.isEmpty(cityCode)) {
            throw new CustomException("城市编码不能为空");
        }
        if (StrUtil.isEmpty(fileName)) {
            throw new CustomException("文件名称不能为空");
        }
        //get List
        List<Map<String, Object>> cityRateQuery = getCityRateQuery(beginTimeStr, endTimeStr, cityCode);
        HSSFWorkbook wb = new HSSFWorkbook();
        //一个工作表格（sheet）
        HSSFSheet sheet = wb.createSheet("sheet1");
        sheet.setColumnWidth(0, 3500);
        sheet.setColumnWidth(1, 3500);
        HSSFCellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER); //文字水平居中
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setAlignment(XSSFCellStyle.ALIGN_CENTER); //文字水平居中
        style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//文字垂直居中

        HSSFRow row = sheet.createRow(0);
        HSSFCell cell1 = row.createCell(0);
        HSSFCell cell2 = row.createCell(1);
        cell1.setCellValue("城市");
        cell1.setCellStyle(style);
        cell2.setCellStyle(style);
        cell2.setCellValue("指标");
        List<String> days = DateUtils.getDays(beginTimeStr, endTimeStr);
        //第0行
        for (int i = 0; i < days.size(); i++) {
            HSSFCell cell = row.createCell(i + 2);
            sheet.setColumnWidth(i + 2, 3500);
            cell.setCellValue(days.get(i));
            cell.setCellStyle(style);
        }
        List<String> citys = Arrays.asList(cityCode.split(","));


        //column
        Row rows1 = null;
        Row rows2 = null;
        Row rows3 = null;
        Row rows4 = null;
        Row rows5 = null;
        Row rows6 = null;
        Row rows7 = null;
        Row rows8 = null;

        List<String> comparp = Arrays.asList("aqi", "so2", "no2", "co", "pm10", "pm2.5", "primary_pollutant", "o3");
        for (int a = 1; a <= citys.size(); a++) {
            rows1 = sheet.createRow(((a - 1) * 8 + 1));
            //citycode
            String citycode = citys.get(a - 1);
            for (String comp : comparp) {
                switch (comp) {
                    case "aqi":
                        rows1.createCell(0).setCellValue(citycode);
                        rows1.createCell(1).setCellValue("AQI");
                        rows1.getCell(1).setCellStyle(style);
                        break;
                    case "so2":
                        rows2 = sheet.createRow(((a - 1) * 8 + 2));
                        rows2.createCell(1).setCellValue("SO2");
                        rows2.getCell(1).setCellStyle(style);
                        break;
                    case "no2":
                        rows3 = sheet.createRow(((a - 1) * 8 + 3));
                        rows3.createCell(1).setCellValue("NO2");
                        rows3.getCell(1).setCellStyle(style);
                        break;
                    case "co":
                        rows4 = sheet.createRow(((a - 1) * 8 + 4));
                        rows4.createCell(1).setCellValue("CO");
                        rows4.getCell(1).setCellStyle(style);
                        break;
                    case "pm10":
                        rows5 = sheet.createRow(((a - 1) * 8 + 5));
                        rows5.createCell(1).setCellValue("PM10");
                        rows5.getCell(1).setCellStyle(style);
                        break;
                    case "pm2.5":
                        rows6 = sheet.createRow(((a - 1) * 8 + 6));
                        rows6.createCell(1).setCellValue("PM2.5");
                        rows6.getCell(1).setCellStyle(style);
                        break;
                    case "o3":
                        rows7 = sheet.createRow(((a - 1) * 8 + 7));
                        rows7.createCell(1).setCellValue("O3");
                        rows7.getCell(1).setCellStyle(style);
                        break;
                    case "primary_pollutant":
                        rows8 = sheet.createRow(((a - 1) * 8 + 8));
                        rows8.createCell(1).setCellValue("首污");
                        rows8.createCell(0).setCellStyle(style);
                        rows8.getCell(1).setCellStyle(style);
                        break;
                }

            }
            //给每个单元格赋值
            for (int i = 2; i < days.size() + 2; i++) {
                String code = citys.get(a - 1);
                String day = days.get(i - 2);
                for (String str : comparp) {
                    switch (str) {
                        case "aqi":
                            String[] value1 = getExcelCellValue(code, day, str, cityRateQuery);
                            transCell(wb, value1, i, rows1, str);
                            break;
                        case "so2":
                            String[] value2 = getExcelCellValue(code, day, "so2", cityRateQuery);
                            transCell(wb, value2, i, rows2, "so2");
                            break;
                        case "no2":
                            String[] value3 = getExcelCellValue(code, day, "no2", cityRateQuery);
                            transCell(wb, value3, i, rows3, "no2");
                            break;
                        case "co":
                            String[] value4 = getExcelCellValue(code, day, "co", cityRateQuery);
                            transCell(wb, value4, i, rows4, "co");
                            break;
                        case "pm10":
                            String[] value5 = getExcelCellValue(code, day, "pm10", cityRateQuery);
                            transCell(wb, value5, i, rows5, "pm10");
                            break;
                        case "pm2.5":
                            String[] value6 = getExcelCellValue(code, day, "pm2.5", cityRateQuery);
                            transCell(wb, value6, i, rows6, "pm2.5");
                            break;
                        case "o3":
                            String[] value7 = getExcelCellValue(code, day, "o3", cityRateQuery);
                            transCell(wb, value7, i, rows7, "o3");
                            break;
                        case "primary_pollutant":
                            String[] value8 = getExcelCellValue(code, day, "primary_pollutant", cityRateQuery);
                            transCell(wb, value8, i, rows8, "primary_pollutant");
                            break;

                    }

                }

            }
            //合并好居中显示
            int firstRow = ((a - 1) * 8 + 1);
            int lastRow = ((a - 1) * 8 + 8);
            CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, 0, 0);
            sheet.addMergedRegion(region);
            sheet.getRow(firstRow).getCell(0).setCellStyle(style2);

        }
        FileOutputStream out = new FileOutputStream("/Users/jiatp/study/work/" + fileName + "-" + System.currentTimeMillis() + ".xls");
        wb.write(out);
        //流的关闭
        out.close();

    }


    /**
     * @param wb    poi对象
     * @param value 赋值数组
     * @param i     行数
     * @param row   row对象
     * @Description: //TODO
     * @Author: Jiatp
     * @Date: 2022/5/11 6:14 下午
     * @return: org.apache.poi.ss.usermodel.Cell
     */
    public void transCell(HSSFWorkbook wb, String[] value, int i, Row row, String type) {
        if ("primary_pollutant".equals(type)) {
            //单独处理首污的背景颜色
            HSSFCellStyle style = ExcelUtils.fillPrimaryPolColor(wb, value[0]);
            Cell cell = row.createCell(i);
            cell.setCellValue(value[0]);
            cell.setCellStyle(style);
        } else {
            HSSFCellStyle style = ExcelUtils.fillColor(wb, value[1]);
            Cell cell = row.createCell(i);
            cell.setCellValue(value[0]);
            cell.setCellStyle(style);
        }

    }

    /**
     * @param cityCode
     * @param day
     * @param type
     * @param cityRateQuery
     * @Description: //TODO 计算出每一个单元格的值
     * @Author: Jiatp
     * @Date: 2022/5/11 2:36 下午
     * @return: java.lang.String[]
     */
    public String[] getExcelCellValue(String cityCode, String day, String type, List<Map<String, Object>> cityRateQuery) {
        String[] str = null;
        for (Map<String, Object> map : cityRateQuery) {
            String code = map.get("code").toString();
            String datadate = map.get("datadate").toString();
            if (cityCode.equals(code) && day.equals(datadate)) {
                switch (type) {
                    case "aqi":
                        String _aqi = map.get("aqi").toString();
                        str = new String[]{_aqi, _aqi};
                        break;
                    case "so2":
                        String _so2 = map.get("so2").toString();
                        String _iaqi1 = map.get("so2_aqi").toString();
                        str = new String[]{_so2, _iaqi1};
                        break;
                    case "no2":
                        String _no2 = map.get("no2").toString();
                        String _iaqi2 = map.get("no2_aqi").toString();
                        str = new String[]{_no2, _iaqi2};
                        break;
                    case "co":
                        String _co = map.get("co").toString();
                        String _iaqi3 = map.get("co_aqi").toString();
                        str = new String[]{_co, _iaqi3};
                        break;
                    case "pm10":
                        String _pm10 = map.get("pm10").toString();
                        String _iaqi4 = map.get("pm10_aqi").toString();
                        str = new String[]{_pm10, _iaqi4};
                        break;
                    case "pm2.5":
                        String _pm25 = map.get("pm25").toString();
                        String _iaqi5 = map.get("pm25_aqi").toString();
                        str = new String[]{_pm25, _iaqi5};
                        break;
                    case "o3":
                        String _o3 = map.get("o3").toString();
                        String _iaqi6 = map.get("o3_aqi").toString();
                        str = new String[]{_o3, _iaqi6};
                        break;
                    case "primary_pollutant":
                        String _primary_pollutant = map.get("primary_pollutant").toString();
                        str = new String[]{_primary_pollutant, _primary_pollutant};
                        break;
                }

            }

        }
        return str;
    }


    /**
     * @param beginTimeStr
     * @param endTimeStr
     * @param cityCode
     * @Description: //TODO 计算城市变化趋势 环比和同比
     * @Author: Jiatp
     * @Date: 2022/5/12 8:48 上午
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    @Override
    public List<Map<String, Object>> getCityRateQuery(String beginTimeStr, String endTimeStr, String cityCode) throws CustomException, ParseException {

        if (StrUtil.isEmpty(beginTimeStr) || StrUtil.isEmpty(endTimeStr)) {
            throw new CustomException("开始时间和结束时间不能为空！");
        }
        if (StrUtil.isEmpty(cityCode)) {
            throw new CustomException("城市编码不能为空！");
        }
        List<Map<String, Object>> currentCityDayAvg = getAqiAvg(beginTimeStr, endTimeStr, cityCode);
        //2 计算同比
        String beginStrTime = DateUtils.monthOverMonth(beginTimeStr, DateUtils.SUB);
        String endStrTime = DateUtils.monthOverMonth(endTimeStr, DateUtils.SUB);

        List<Map<String, Object>> monthOverCityAvg = getAqiAvg(beginStrTime, endStrTime, cityCode);
        //去计算每个同比
        List<Map<String, Object>> maps = AqiUtils.calMonthOver(currentCityDayAvg, monthOverCityAvg, AqiUtils.TYPE_A);

        //计算环比
        String beginStrDay = DateUtils.calOverDay(beginTimeStr, DateUtils.SUB);
        String endStrDay = DateUtils.calOverDay(endTimeStr, DateUtils.SUB);

        List<Map<String, Object>> dayOverCityAvg = getAqiAvg(beginStrDay, endStrDay, cityCode);
        List<Map<String, Object>> value = AqiUtils.calMonthOver(maps, dayOverCityAvg, AqiUtils.TYPE_B);
        List<Map<String, Object>> result = AqiUtils.orderMapByAqi(value);
        List<Map<String, Object>> rS = AqiUtils.orderMapByLevel(result);
        return rS;
    }


    /**
     * @param beginTimeStr
     * @param endTimeStr
     * @param cityCode
     * @Description: //TODO 计算多城市日均
     * @Author: Jiatp
     * @Date: 2022/5/12 8:48 上午
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    @Override
    public List<Map<String, Object>> getAqiAvg(String beginTimeStr, String endTimeStr, String cityCode)
            throws CustomException, ParseException {

        if (StrUtil.isEmpty(beginTimeStr) || StrUtil.isEmpty(endTimeStr)) {
            throw new CustomException("开始时间和结束时间不能为空！");
        }
        if (StrUtil.isEmpty(cityCode)) {
            throw new CustomException("城市编码不能为空！");
        }
        List<String> citys = Arrays.asList(cityCode.split(","));
        //select
        QueryWrapper<TscPollutantcHour> wrapper = new QueryWrapper<>();
        wrapper.in("citycode", citys);
        wrapper.between("monitordate", Timestamp.valueOf(beginTimeStr), Timestamp.valueOf(endTimeStr));
        List<TscPollutantcHour> tscPollutantcHours = tScPollutantcHourMapper.selectList(wrapper);
        //按照城市分组
        Map<String, List<TscPollutantcHour>> groupMap = tscPollutantcHours.stream().collect(Collectors.groupingBy(TscPollutantcHour::getCityCode));
        //resultSet
        List<Map<String, Object>> rS = new ArrayList<>();
        for (Map.Entry<String, List<TscPollutantcHour>> entry : groupMap.entrySet()) {
            String city = entry.getKey();
            List<TscPollutantcHour> value = entry.getValue();
            //按照城市的时间分组
            Map<String, List<TscPollutantcHour>> collect = value.stream().sorted(Comparator.comparing(TscPollutantcHour::getMonitorDate))
                    .collect(Collectors.groupingBy(o -> o.transDate(o.getMonitorDate())));
            for (Map.Entry<String, List<TscPollutantcHour>> entry2 : collect.entrySet()) {
                log.info("key= " + entry2.getKey() + " and value= " + entry2.getValue());
                //当天
                String day = entry2.getKey()+" 00:00:00";
                List<TscPollutantcHour> ts = entry2.getValue();
                Map<String, Object> result = AqiUtils.getCityDayAvg(day, ts);
                result.put("code", city);
                rS.add(result);
            }
        }
        //将Rs集合中的数据重新排序
        List<Map<String, Object>> maps = AqiUtils.orderMapByDate(rS);
        return maps;
    }


    /**
     * @param type
     * @param value
     * @Description: //TODO 计算所对应污染物的信息
     * @Author: Jiatp
     * @Date: 2022/5/12 8:48 上午
     * @return: org.example.clear3.domain.vo.AqiVO
     */
    @Override
    public AqiVO getAqiMsg(String type, String value) throws CustomException {
        if (StrUtil.isBlank(type)) {
            throw new NullPointerException("污染物类型不能为空！");
        }
        if (StrUtil.isBlank(value)) {
            throw new NullPointerException("污染物浓度值不能为空！");
        }
        String name = AQITypeEnum.getName(type);
        if (AQITypeEnum.unKnow.getName().equals(name)) {
            throw new CustomException("污染物类型未知！");
        }
        AqiVO aQlTable = AqiUtils.getAQlTable(type, value);
        return aQlTable;
    }
}
