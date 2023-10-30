package com.katana.test.plugin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created on 2022/10/24
 *
 * @author liyifei
 */
@Component
public class PluginB implements IPlugin {

    @Value("${app.version:V*}")
    private String version;

    @Override
    public String toString() {
        return "PluginB{" +
                "version='" + version + '\'' +
                '}';
    }

    @Override
    public void run() {
        System.out.println(this);
    }

}
