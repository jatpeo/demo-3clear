package org.example.clear3.util;

import cn.hutool.core.util.StrUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.example.clear3.constant.AqiContants;

import java.util.LinkedList;
import java.util.List;

/**
 * @program: demo-3clear
 * @description: esayPoi导入导出工具包
 * @author: Jiatp
 * @create: 2022-05-10 10:41
 **/
public class ExcelUtils {


    /**
     * @param
     * @Description: //TODO 根据列数生成行
     * @Author: Jiatp
     * @Date: 2022/5/11 5:38 下午
     * @return: org.apache.poi.ss.usermodel.Row
     */
    public static List<Row> createRow(HSSFSheet sheet, int number) {
        List<Row> rows = new LinkedList<>();
        for (int i = 1; i <= number; i++) {
            HSSFRow row = sheet.createRow(i);
            rows.add(row);
        }
        return rows;
    }


    /**
     * @param wb
     * @param value
     * @Description: //TODO 处理臭氧的污染颜色
     * @Author: Jiatp
     * @Date: 2022/5/11 4:32 下午
     * @return: org.apache.poi.hssf.usermodel.HSSFCellStyle
     */
    public static HSSFCellStyle fillPrimaryPolColor(HSSFWorkbook wb, String value) {

        if (StrUtil.isNotEmpty(value)) {
            if (AqiContants.PM10.equals(value)) {
                HSSFCellStyle style = wb.createCellStyle();
                style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
               // style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                return style;
            } else if (AqiContants.PM25.equals(value)) {
                HSSFCellStyle style = wb.createCellStyle();
                style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
              //  style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                return style;
            } else if (AqiContants.O3.equals(value)) {
                HSSFCellStyle style = wb.createCellStyle();
                style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
              //  style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                return style;
            } else if (AqiContants.NO2.equals(value)) {
                HSSFCellStyle style = wb.createCellStyle();
                style.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
             //   style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                return style;
            } else if (AqiContants.SO2.equals(value)) {
                HSSFCellStyle style = wb.createCellStyle();
                style.setFillForegroundColor(IndexedColors.RED.getIndex());
              //  style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                return style;
            } else if (AqiContants.CO.equals(value)) {
                HSSFCellStyle style = wb.createCellStyle();
                style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
              //  style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                return style;
            } else {
                HSSFCellStyle style = wb.createCellStyle();
                style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
             //   style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
            }

        }

        return null;
    }

    /**
     * @param wb
     * @param value
     * @Description: //TODO 处理六项值的背景颜色
     * @Author: Jiatp
     * @Date: 2022/5/11 4:31 下午
     * @return: org.apache.poi.hssf.usermodel.HSSFCellStyle
     */

    public static HSSFCellStyle fillColor(HSSFWorkbook wb, String value) {
        double rs = Double.valueOf(value);
        if (rs <= AqiContants.AQIBASEVALUE[2]) {
            //绿色
            HSSFCellStyle style = wb.createCellStyle();
            style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
           // style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            return style;
        } else if (rs >= AqiContants.AQIBASEVALUE[2] && rs <= AqiContants.AQIBASEVALUE[3]) {
            //yello
            HSSFCellStyle style = wb.createCellStyle();
            style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            //style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            return style;
        } else if (rs >= AqiContants.AQIBASEVALUE[4] && rs <= AqiContants.AQIBASEVALUE[5]) {
            //orange
            HSSFCellStyle style = wb.createCellStyle();
            style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
           // style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            return style;
        } else if (rs >= AqiContants.AQIBASEVALUE[6] && rs <= AqiContants.AQIBASEVALUE[7]) {
            //red
            HSSFCellStyle style = wb.createCellStyle();
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
         //   style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            return style;
        } else if (rs >= AqiContants.AQIBASEVALUE[8] && rs <= AqiContants.AQIBASEVALUE[9]) {
            //purple
            HSSFCellStyle style = wb.createCellStyle();
            style.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
          //  style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            return style;
        } else if (rs >= AqiContants.AQIBASEVALUE[10]) {
            //褐色
            HSSFCellStyle style = wb.createCellStyle();
            style.setFillForegroundColor(IndexedColors.MAROON.getIndex());
          //  style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            return style;
        }
        return null;
    }

}
