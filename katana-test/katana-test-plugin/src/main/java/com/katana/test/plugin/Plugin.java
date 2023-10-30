package com.katana.test.plugin;

/**
 * Created on 2022/10/25
 *
 * @author skyfalling
 */

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 用于声明插件定义
 *
 * @author liyifei
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
@Inherited
public @interface Plugin {

    /**
     * UDF名称,默认值{@link Class#getSimpleName()}的驼峰形式
     *
     * @return UDF别名
     */
    String value() default "";

}
