package org.example.clear3.util;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Jiatp
 * @Description //TODO 计算O3的滑动8小时和滑动8小时最大
 * @Date 5:04 下午 2022/5/7
 **/
public class O3Utils {


    /**
     * 臭氧滑动8最小
     */
    private static final int MIN_HOUR = 8;

    /**
     * 臭氧滑动8最大
     */
    private static final int MAX_HOUR = 24;

    /**
     * @return List<Double>
     * @Author Jiatp
     * @Description //TODO 计算点8小时平均
     * @Date 5:06 下午 2022/5/7
     * @Param int [] data
     **/
    public static List<Double> get8Avg(Double[] data) {
        List<Double> list = new LinkedList<>();
        for (int i = O3Utils.MIN_HOUR; i < O3Utils.MAX_HOUR; i++) {
            int min = i - 8;
            Double[] test = new Double[8];
            System.arraycopy(data, min, test, 0, 8);
            List<Double> collect = Arrays.stream(test).collect(Collectors.toList());
            DoubleSummaryStatistics statistics = collect.stream().mapToDouble(Number::doubleValue).summaryStatistics();
            String val = ComCalUtil.sciCal(statistics.getAverage(), 0);
            list.add(Double.valueOf(val));
        }
        return list;
    }

    /**
     * @return Double
     * @Author Jiatp
     * @Description //TODO 计算滑动8小时最大
     * @Date 5:04 下午 2022/5/7
     * @Param list
     **/
    public static Double get8MaxAvg(List<Double> list) {
        Double max = list.stream().reduce(list.get(0), Double::max);
        return max;
    }
}
