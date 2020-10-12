package com.prj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//@MapperScan("com.prj.mapper")
@ServletComponentScan
public class VueblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(VueblogApplication.class, args);
    }

}
