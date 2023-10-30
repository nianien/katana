package com.katana.spring.loader;

import com.katana.spring.resolver.ClassLoaderResourcePatternResolver;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationImportSelector;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.EnvironmentPostProcessorsFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.type.StandardAnnotationMetadata;

import java.io.File;
import java.util.List;
import java.util.function.Supplier;

/**
 * 动态加载Spring容器，实现容器隔离和类加载器隔离
 *
 * @author skyfalling
 */
@AllArgsConstructor
public class ApplicationContextLoader {

    private AnnotationConfigApplicationContext originContext;

    private final String[] dynamicClassExcludes = new String[]{".*\\$\\$SpringCGLIB\\$\\$.*"};


    /**
     * 动态加载Spring容器
     *
     * @param file
     * @return
     */
    @SneakyThrows
    public AnnotationConfigApplicationContext load(File file) {
        AnnotationConfigApplicationContext context = createContext();
        ConfigurableEnvironment environment = context.getEnvironment();
        ClassLoader classLoader = new FileClassLoader(file, originContext.getClassLoader(), dynamicClassExcludes);
        ClassLoaderResourcePatternResolver resourceLoader = new ClassLoaderResourcePatternResolver(classLoader);
        Thread.currentThread().setContextClassLoader(classLoader);
        SpringApplication sa = new SpringApplication(resourceLoader);
        List<EnvironmentPostProcessor> processors = EnvironmentPostProcessorsFactory.fromSpringFactories(originContext.getClassLoader()).getEnvironmentPostProcessors(Supplier::get, new DefaultBootstrapContext());
        for (EnvironmentPostProcessor processor : processors) {
            processor.postProcessEnvironment(environment, sa);
        }

        //注册自动配置类
        String[] configurations = getConfigurations(file);
        for (String configuration : configurations) {
            context.register(classLoader.loadClass(configuration));
        }
        context.setClassLoader(classLoader);
        context.setResourceLoader(resourceLoader);
        //设置父容器
        //通过context#getBean和context#getBeanNamesForType可以获取父容器中的Bean，
        //通过context#getBeanDefinitionNames只能获取当前容器定义的bean
        context.setParent(originContext);
        //复用原容器中的BeanFactoryPostProcessor,处理新注册的Bean
        context.getBeanFactoryPostProcessors().addAll(originContext.getBeanFactoryPostProcessors());
        context.refresh();
        return context;
    }


    private static String[] getConfigurations(File file) {
        @EnableAutoConfiguration
        class NoScan {
            //用于扫描META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports,该类定义在方法中,是为了避免扫描当前类时被加载
        }
        FileClassLoader classLoader = new FileClassLoader(file);
        AutoConfigurationImportSelector selector = new AutoConfigurationImportSelector();
        selector.setBeanClassLoader(classLoader);
        selector.setResourceLoader(new ClassLoaderResourcePatternResolver(classLoader));
        selector.setEnvironment(new StandardEnvironment());
        String[] configurations = selector.selectImports(new StandardAnnotationMetadata(NoScan.class));
        return configurations;
    }


    private AnnotationConfigApplicationContext createContext() {
        return new AnnotationConfigApplicationContext(
                new DefaultListableBeanFactory() {
                    @Override
                    public void preInstantiateSingletons() throws BeansException {
                        try {
                            //避免class not exists
                            super.preInstantiateSingletons();
                        } catch (BeansException e) {
                            //e.printStackTrace();
                        }
                    }
                }
        ) {
            //关闭容器
            protected void doClose() {
                // Destroy all cached singletons in the context's BeanFactory.
                destroyBeans();

                // Close the state of this context itself.
                closeBeanFactory();

                // Let subclasses do some final clean-up if they wish...
                onClose();
            }
        };
    }


}
