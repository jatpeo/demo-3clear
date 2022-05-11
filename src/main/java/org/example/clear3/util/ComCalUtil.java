package org.example.clear3.util;

import java.math.BigDecimal;
import java.util.*;

/**
 * @program: demo-3clear
 * @description: 工具类
 * @author: Jiatp
 * @create: 2022-05-09 12:04
 **/
public class ComCalUtil {

    /**
     * @param value 需要科学计算的数据
     * @param digit 保留的小数位
     * @return
     * 功能：四舍六入五成双计算法
     */
    public static String sciCal(double value, int digit){
        String result = "-999";
        try {
            double ratio = Math.pow(10, digit);
            double _num = value * ratio;
            double mod = _num % 1;
            double integer = Math.floor(_num);
            double returnNum;
            if(mod > 0.5){
                returnNum=(integer + 1) / ratio;
            }else if(mod < 0.5){
                returnNum=integer / ratio;
            }else{
                returnNum=(integer % 2 == 0 ? integer : integer + 1) / ratio;
            }
            BigDecimal bg = new BigDecimal(returnNum);
            result = bg.setScale((int)digit, BigDecimal.ROUND_HALF_UP).toString();
        } catch (RuntimeException e) {
            throw e;
        }
        return result;
    }


    /**
     * @auther: Jiatp
     * @desp：// TODO: 计算环比 日
     * @date: 2022/5/9 3:30 下午
     * @param: @param	null
     * @return: String
     */
    public static String calDayOver(double curr, double last) {
        double value = (curr - last) / last;
        String result = ComCalUtil.sciCal(value * 100,0)+"%";
        return result;
    }

    /**
     * @auther: Jiatp
     * @desp：// TODO: 计算同比的 月
     * @date: 2022/5/9 3:30 下午
     * @param: @param	null
     * @return: String
     */
    public static String calMonthOver(double curr, double last) {
        double value = (curr - last) / last;
        String result = ComCalUtil.sciCal(value * 100,0)+"%";
        return result;
    }


}
