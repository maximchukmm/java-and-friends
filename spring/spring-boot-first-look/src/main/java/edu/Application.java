package edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Based on articles
 * <p>
 * https://www.baeldung.com/spring-boot-start
 * <p>
 * https://www.baeldung.com/spring-boot-testing
 */
//todo move to separate configuration and @Import it here
// @EnableJpaRepositories("edu.persistence.repo")
// @EntityScan("edu.persistence.model")
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
