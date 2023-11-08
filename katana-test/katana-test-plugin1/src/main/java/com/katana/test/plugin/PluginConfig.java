package com.katana.test.plugin;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created on 2022/11/1
 *
 * @author liyifei
 */
@EnableAspectJAutoProxy
@EnableAutoConfiguration
@ComponentScan("com.katana.test.plugin")
@Configuration
public class PluginConfig {

}
