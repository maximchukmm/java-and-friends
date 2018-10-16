package spring.taskexecutingandscheduling;

import spring.taskexecutingandscheduling.contextdemo.ApplicationContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;

public class RunnableTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(RunnableTask.class);

    private String message;

    public RunnableTask(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        log.info("This message \"{}\" at {} from thread {}", message, Instant.now(), Thread.currentThread().getName());
        List<String> names = (List<String>) ApplicationContextUtils.getBean("names");
        log.info("Thread {} get access to resource {}", Thread.currentThread().getName(), names);
    }
}
