package com.songxc.springboot.prometheus.metrics.threadpool;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

public class MetricsBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Set<MethodMetadata> annotatedMethods = importingClassMetadata.getAnnotatedMethods(Bean.class.getName());
        if (annotatedMethods != null && annotatedMethods.size() > 0) {
            annotatedMethods.forEach(m -> {
                if (ThreadPoolTaskExecutor.class.getName().equals(m.getReturnTypeName()) ||
                        ThreadPoolExecutor.class.getName().equals(m.getReturnTypeName())) {
                    Map<String, Object> attributes = m.getAnnotationAttributes(Bean.class.getName());
                    if (attributes != null) {
                        String beanName = m.getMethodName();
                        String[] names = (String[]) attributes.get("name");
                        if (names != null && names.length > 0) {
                            beanName = names[0];
                        }
                        ThreadPoolMetricsMonitor.addBeanName(beanName);
                    }
                }
            });
        }
    }
}
