package edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Based on articles
 * <p>
 * https://www.baeldung.com/spring-boot-start
 * <p>
 * https://www.baeldung.com/spring-boot-testing
 */
@EnableJpaRepositories("edu.persistence.repo")
@EntityScan("edu.persistence.model")
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
