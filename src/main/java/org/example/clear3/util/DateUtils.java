package org.example.clear3.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @program: demo-3clear
 * @description: 日期工具类
 * @author: Jiatp
 * @create: 2022-05-09 13:52
 **/
@Slf4j
public class DateUtils {

    public static final String ADD = "+";
    public static final String SUB = "-";


    /**
     * @Description: //TODO 得到两个日期之前的日期
     * @Author: Jiatp
     * @Date: 2022/5/10 5:18 下午
     * @param startTime
     * @param endTime
     * @return: java.util.List<java.lang.String>
     */
    public static List<String> getDays(String startTime, String endTime) {
        // 返回的日期集合
        List<String> days = new LinkedList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            // 日期加1(包含结束)
            tempEnd.add(Calendar.DATE, +1);
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }
    /**
     * @auther: Jiatp
     * @desp：// TODO: 计算同比工具类  month
     * @date: 2022/5/9 1:53 下午
     * @param: @param	beginTimeStr，endTimeStr
     * @return: String
     */
    public static String monthOverMonth(String timeStr, String type) throws ParseException {
        if (StrUtil.isEmpty(timeStr)) {
            throw new NullPointerException("日期不能为空！");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //前一个
        Date time = sdf.parse(timeStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        //当前时间减去一月，即一月前的时间
        if (DateUtils.ADD.equals(type)) {
            cal.add(Calendar.MONTH, 1);
        } else if (DateUtils.SUB.equals(type)) {
            cal.add(Calendar.MONTH, -1);
        }
        String result = sdf.format(cal.getTime());
        return result;
    }

    /**
     * @auther: Jiatp
     * @desp：// TODO: 环比上一日
     * @date: 2022/5/9 4:30 下午
     * @param: @param	null
     * @return:
     */
    public static String calOverDay(String timeStr, String type) throws ParseException {
        if (StrUtil.isEmpty(timeStr)) {
            throw new NullPointerException("日期不能为空！");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = sdf.parse(timeStr);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        if (DateUtils.ADD.equals(type)) {
            rightNow.add(Calendar.DAY_OF_MONTH, 1);
        } else if (DateUtils.SUB.equals(type)) {
            rightNow.add(Calendar.DAY_OF_MONTH, -1);
        }
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }
}
