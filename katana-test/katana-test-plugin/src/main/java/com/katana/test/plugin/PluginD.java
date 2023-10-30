package com.katana.test.plugin;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Created on 2022/10/24
 *
 * @author liyifei
 */
@Component
public class PluginD implements IPlugin {


    @Value("${app.version:V*}")
    private String version;
    @Resource
    private IPlugin pluginA;

    @Resource
    private PluginB pluginB;

    @Override
    public String toString() {
        return "PluginD{" +
                "version='" + version + '\'' +
                ", pluginA=" + pluginA +
                ", pluginB=" + pluginB +
                '}';
    }

    @Override
    public void run() {
        System.out.println(this);
    }
}
