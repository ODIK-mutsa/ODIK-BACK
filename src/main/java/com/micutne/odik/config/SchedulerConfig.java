package com.micutne.odik.config;

import com.micutne.odik.common.scheduler.ExpiredEmailScheduler;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Bean
    public JobDetailFactoryBean expiredEmailJobDetail() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(ExpiredEmailScheduler.class);
        factory.setDurability(true);
        return factory;
    }

//    /**
//     * 실행 시간 기준 Interval 간격
//     */
//    @Bean
//    public SimpleTriggerFactoryBean anotherTrigger(JobDetail expiredEmailJobDetail) {
//        SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
//        factory.setJobDetail(expiredEmailJobDetail);
//        factory.setRepeatInterval(10 * 60 * 1000); // 12 hours in milliseconds
//        factory.setStartDelay(0);
//        return factory;
//    }

    /**
     * cron 사용
     */
    @Bean
    public CronTriggerFactoryBean expiredEmailTrigger(JobDetail expiredEmailJobDetail) {
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        factory.setJobDetail(expiredEmailJobDetail);
        factory.setCronExpression("0 0 12 * * ?"); // 매일 정각 12시에 실행
        return factory;
    }

}
