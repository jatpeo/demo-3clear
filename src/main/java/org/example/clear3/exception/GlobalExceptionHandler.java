package org.example.clear3.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.example.clear3.util.RespBean;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * @Description: 全局异常处理   比如删除 外键无法删除
 * @Author: Jatpeo
 * @Create: 2021-05-20 22:29
 **/
@Slf4j
@Data
@RestControllerAdvice
public class GlobalExceptionHandler {

    private String code;
    private String Msg;

    @ExceptionHandler({Exception.class})
    public RespBean sqlException(SQLException e) {
        return RespBean.error("数据库异常，操作失败！"+e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespBean notValidR(MethodArgumentNotValidException e)
    {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        log.error("参数异常："+message);
        return RespBean.error(message);
    }

}
