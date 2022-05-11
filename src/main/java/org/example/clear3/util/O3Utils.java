package org.example.clear3.util;

import java.util.LinkedList;
import java.util.List;

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
    public static List<Double> get8Avg(double[] data) {
        List<Double> list = new LinkedList<>();
        for (int i = O3Utils.MIN_HOUR; i <= O3Utils.MAX_HOUR; i++) {
            int min = i - 8;
            double[] test = new double[8];
            System.arraycopy(data, min, test, 0, 8);
            int sum = 0;
            for (double m : test) {
                sum += m;
            }
            double values = Double.valueOf(ComCalUtil.sciCal(sum / 8,0));
            list.add(values);
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
