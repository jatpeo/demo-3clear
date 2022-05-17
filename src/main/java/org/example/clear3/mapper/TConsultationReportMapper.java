package org.example.clear3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.example.clear3.domain.TConsultationReport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TConsultationReportMapper extends BaseMapper<TConsultationReport>{


    /**
     * @Description: //TODO 批量保存数据
     * @Author: Jiatp
     * @Date: 2022/5/17 4:52 下午
     * @param record
     */
    void insertBatchData(@Param("list") List<TConsultationReport> record);


}