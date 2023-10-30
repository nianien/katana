package com.katana.test;

import com.cudrania.core.reflection.Reflections;
import com.katana.spring.loader.ApplicationContextLoader;
import com.katana.test.plugin.PluginD;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.beans.factory.BeanFactoryUtils.beansOfTypeIncludingAncestors;

/**
 * Created on 2022/3/25
 *
 * @author liyifei
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@Slf4j
public class MyApplication implements CommandLineRunner {


    @Resource
    private AnnotationConfigApplicationContext originContext;


    private Map<String, ConfigurableApplicationContext> contextMap = new HashMap<>();


    private ClassLoader originClassLoader;

    public static void main(String[] args) throws Exception {

        try {
            new SpringApplicationBuilder()
                    .sources(MyApplication.class)
                    .web(WebApplicationType.NONE)
                    .run(args);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }


    @jakarta.annotation.PostConstruct
    private void init() {
        originClassLoader = originContext.getClassLoader();
        System.out.println("==>context:" + originContext);

    }


    @SneakyThrows
    @Override
    public void run(String... args) {
        for (String definitionName : originContext.getBeanDefinitionNames()) {
            AbstractBeanDefinition beanDefinition = (AbstractBeanDefinition) originContext.getBeanDefinition(definitionName);
            beansOfTypeIncludingAncestors(originContext, beanDefinition.resolveBeanClass(originClassLoader), true, true);
        }
        String[] urls = new String[]{
                "/Users/skyfalling/Workspace/skyfalling/katana/katana-test/katana-test-plugin1/target/katana-test-plugin1-1.0.0-SNAPSHOT.jar",
                "/Users/skyfalling/Workspace/skyfalling/katana/katana-test/katana-test-plugin2/target/katana-test-plugin2-1.0.0-SNAPSHOT.jar",
        };
        show(originContext);
        for (String url : urls) {
            ConfigurableApplicationContext context1 = loadJar(url);
            show(context1);
        }
        System.out.println("======end=========");
    }


    private void show(ConfigurableApplicationContext context) {
        log.info("===>show beans defined in {}@{}:", context.getClass(), System.identityHashCode(context));
        Arrays.stream(context.getBeanDefinitionNames()).forEach(e ->
        {
            String k = e;
            Object v = context.getBean(k);

            if (v.getClass().getName().startsWith("com.katana") || k.contains("plugin")) {
                log.info("==>bean[{}:{}@{}]{}", k, v.getClass(), v.getClass().getClassLoader(), v);
                if (k.startsWith("plugin") && !k.equals("pluginConfig")) {
                    Reflections.invoke("run", v, new Object[0]);
                }
            }
        });


    }

    @SneakyThrows
    private ConfigurableApplicationContext loadJar(String url) {
        System.out.println("load " + url + " ...");
        ConfigurableApplicationContext context = new ApplicationContextLoader(originContext).load(new File(url));
        contextMap.put(url, context);
        return context;
    }


    private void unloadJar(String url) {
        log.info("==>unload " + url + " ...");
        ConfigurableApplicationContext context = contextMap.remove(url);
        if (context != null) {
            context.close();
        }
    }


}
