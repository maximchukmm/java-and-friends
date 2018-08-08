package edu.frameworks.org.spring.taskexecutingandscheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TaskSchedulerService {

    private static final Logger log = LoggerFactory.getLogger(TaskSchedulerService.class);

    private TaskScheduler taskScheduler;

    @Autowired
    public TaskSchedulerService(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void scheduleTasksOnTime() {
        log.info("Scheduling tasks...");
        for (int i = 15; i >= 0; i--) {
            taskScheduler.schedule(
                new RunnableTask("Hello, " + i),
                Instant.now().plusMillis(i));
        }
        log.info("Finish scheduling tasks...");
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void scheduledTaskEveryFiveSeconds() {
        log.info("task every 5 seconds: {}", Instant.now());
    }

    @Scheduled(cron = "*/7 * * * * *")
    public void scheduledTaskEverySevenSeconds() {
        log.info("task every 7 seconds: {}", Instant.now());
    }
}
