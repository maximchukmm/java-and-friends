package edu.frameworks.org.spring.taskexecutingandscheduling;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        TaskSchedulerService taskSchedulerService = ctx.getBean(TaskSchedulerService.class);

//        taskSchedulerService.scheduleTasksOnTime();

        // ((AnnotationConfigApplicationContext) ctx).close();
    }
}
