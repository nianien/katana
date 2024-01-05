package com.katana.webapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主应用
 */
@Slf4j
@RestController
@SpringBootApplication(scanBasePackages = "com.katana")
public class JooqApplication {


    public static void main(String[] args) {
        new SpringApplicationBuilder(JooqApplication.class)
                .web(WebApplicationType.SERVLET)
                .build().run(args);
    }


    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "hello,word!";
    }

}