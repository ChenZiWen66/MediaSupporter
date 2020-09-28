package com.czw.mediasupportmodule;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.czw.mediasupportmodule.mapper"})
@SpringBootApplication
public class MediaSupportModuleApplication {
    private static final Logger LOG = LoggerFactory.getLogger(MediaSupportModuleApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(MediaSupportModuleApplication.class, args);
        LOG.info("创建完成: http://localhost:9001");
    }

}
