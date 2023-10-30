package com.katana.test.plugin;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Created on 2022/11/1
 *
 * @author liyifei
 */
@Component("myConfig")
@ComponentScan({"com.katana.test", "com.katana.spring"})
public class PluginConfig {


}
