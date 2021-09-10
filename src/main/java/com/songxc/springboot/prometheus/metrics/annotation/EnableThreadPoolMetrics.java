package com.songxc.springboot.prometheus.metrics.annotation;

import com.songxc.springboot.prometheus.metrics.threadpool.MetricsBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MetricsBeanDefinitionRegistrar.class)
public @interface EnableThreadPoolMetrics {
}
