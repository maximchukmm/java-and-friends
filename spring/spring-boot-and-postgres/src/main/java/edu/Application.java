package edu;

import edu.service.InitService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @EventListener
    public void init(ApplicationReadyEvent applicationReadyEvent) {
        InitService initService = applicationReadyEvent.getApplicationContext().getBean(InitService.class);
        initService.init();
    }
}
