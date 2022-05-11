package org.example.clear3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author Jiatp
 * @Description //TODO 主启动
 * @Date 7:17 下午 2022/5/7
 **/
@SpringBootApplication
@MapperScan("org.example.clear3.mapper")
public class Demo3ClearApplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo3ClearApplication.class, args);
    }

}
