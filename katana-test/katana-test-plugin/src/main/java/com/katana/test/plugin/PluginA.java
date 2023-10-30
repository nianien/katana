package com.katana.test.plugin;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created on 2022/10/24
 *
 * @author liyifei
 */
@Plugin
public class PluginA implements IPlugin {
    @Value("${app.version:V*}")
    private String version;

    @Override
    public String toString() {
        return "PluginA{" +
                "version='" + version + '\'' +
                '}';
    }

    @Override
    public void run() {
        System.out.println(this);
    }
}
