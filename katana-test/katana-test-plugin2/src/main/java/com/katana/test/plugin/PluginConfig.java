package com.katana.test.plugin;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * Created on 2022/11/1
 *
 * @author liyifei
 */
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAutoConfiguration
@Import(PropertyPlaceholderAutoConfiguration.class)
@Configuration
@ComponentScan("com.katana.test.plugin")
public class PluginConfig {

}
