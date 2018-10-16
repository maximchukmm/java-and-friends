package spring.taskexecutingandscheduling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@Configuration
@ComponentScan(basePackages = {"spring.taskexecutingandscheduling"})
public class ApplicationConfiguration {

    @Bean
    public TaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }

    @Bean("names")
    public List<String> names() {
        List<String> names = new ArrayList<>();
        names.add("John");
        names.add("Ivan");
        names.add("Unknown");
        return names;
    }
}
