package edu.frameworks.org.spring.ripper.awaredemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AwareBeanDemo implements AwareInterfaceDemo {
    private static final Logger log = LoggerFactory.getLogger(AwareBeanDemo.class);

    @Override
    public void printHello() {
        log.info("Hello, AWARE INTERFACE.");
    }

    @Override
    public void printGoodbye() {
        log.info("Goodbye, AWARE INTERFACE!");
    }
}
