package org.example.clear3.service;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.clear3.domain.vo.AQIVo;
import org.example.clear3.exception.CustomException;
import org.example.clear3.enums.AQITypeEnum;
import org.example.clear3.util.AQlUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DemoServiceImpl implements DemoService {

    /**
     * @return
     * @Author Jiatp
     * @Description //TODO  计算所对应污染物的信息
     * @Date 10:34 上午 2022/5/6
     * @Param
     **/
    @Override
    public AQIVo getAqiMsg(String type, String value) throws CustomException {
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
        AQIVo aQlTable = AQlUtils.getAQlTable(type, value);
        return aQlTable;
    }
}
