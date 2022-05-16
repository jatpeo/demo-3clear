package org.example.clear3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.example.clear3.domain.TscPollutantcHour;
import org.example.clear3.domain.param.DayAverageParam;
import org.example.clear3.domain.param.SingleTypeParam;
import org.example.clear3.domain.param.TimeCompareParam;
import org.example.clear3.domain.vo.CityPredictionTimeVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author Jiatp
 * @Description //TODO TScPollutantcHour 基本mapper
 * @Date 8:58 上午 2022/5/7
 **/
@Repository
public interface TscPollutantcHourMapper extends BaseMapper<TscPollutantcHour> {


    /**
     * @param singleTypeParam
     * @Description: //TODO 单个参数下多城市指标查询
     * @Author: Jiatp
     * @Date: 2022/5/13 5:54 下午
     * @return: java.util.List<java.util.Map>
     */
    List<Map> queryPollutionType(@Param("params") SingleTypeParam singleTypeParam);

    /**
     * @param timeCompareParam
     * @Description: //TODO  时段对比查询
     * @Author: Jiatp
     * @Date: 2022/5/13 5:54 下午
     * @return: java.util.List<java.util.Map>
     */

    List<Map> timeCompareQuery(@Param("params") TimeCompareParam timeCompareParam);

    /**
     * @param params
     * @Description: //TODO 基于小时查询多城市的日均值 （新）
     * @Author: Jiatp
     * @Date: 2022/5/13 5:54 下午
     * @return: java.util.List<java.util.Map>
     */
    List<Map> queryDayAverage(@Param("params") DayAverageParam params);

    /**
     * @param cityPredictionTimeVO
     * @Description: TODO 城市逐日预报查询
     * @Author: Jiatp
     * @Date: 2022/5/16 12:03 下午
     * @return: java.util.List<java.util.Map>
     */
    List<Map> cityPredictionByTimeQuery(@Param("params") CityPredictionTimeVO cityPredictionTimeVO);
}
