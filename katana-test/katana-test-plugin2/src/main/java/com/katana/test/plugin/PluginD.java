package com.katana.test.plugin;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created on 2022/10/24
 *
 * @author liyifei
 */
@Plugin
public class PluginD implements IPlugin {

    @Value("${app.version:V*}")
    private String version;

    @Resource
    private PluginA pluginA;

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
