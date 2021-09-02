# Getting Started

### Description
自定义监控采集

* 自定义线程池监控采集  
  采集对象：ThreadPoolTaskExecutor、ThreadPoolExecutor  
  采用定时任务打点，定时任务间隔时间默认5000ms，可配置

### How to use
前提：服务已集成prometheus监控  
```
mvn clean install -DskipTest=true -U
```
引入依赖：  
```
<dependency>
    <groupId>com.songxc</groupId>
    <artifactId>crm-metrics</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
配置间隔时间：  
![image](https://github.com/songxc9527/springboot-prometheus-metrics/blob/main/image/metrics.png)

### Show
![image](https://github.com/songxc9527/springboot-prometheus-metrics/blob/main/image/showMetrics.png)
