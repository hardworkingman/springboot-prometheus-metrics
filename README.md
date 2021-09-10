# Getting Started

### Description
自定义监控采集

* 自定义线程池监控采集  
  采集对象：ThreadPoolTaskExecutor、ThreadPoolExecutor  
  采用定时任务打点，定时任务间隔时间默认5000ms，可配置

### How to use
前提：服务已集成prometheus监控  
```
mvn clean install -DskipTests=true -U
```
引入依赖：  
```
<dependency>
    <groupId>com.songxc</groupId>
    <artifactId>springboot-prometheus-metrics</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
线程池配置类或启动类  
添加注解`@EnableThreadPoolMetrics`
```
@Configuration
@EnableThreadPoolMetrics
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolTaskExecutor testThreadPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("Test-Thread");
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(10);
        executor.setKeepAliveSeconds(10000);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }
}
```
```
@SpringBootApplication
@EnableThreadPoolMetrics
public class ServiceApplication {

    @Bean
    public ThreadPoolTaskExecutor threadPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("Custom-Thread");
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(10);
        executor.setKeepAliveSeconds(10000);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

}
```
配置间隔时间：  
![image](https://github.com/songxc9527/springboot-prometheus-metrics/blob/main/image/metrics.png)

### Show
![image](https://github.com/songxc9527/springboot-prometheus-metrics/blob/main/image/showMetrics.png)
