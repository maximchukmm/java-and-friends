package edu.frameworks.org.spring.spel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("edu.frameworks.org.spring.spel")
class SpelConfiguration {
    @Bean
    ValueAnnotationDemoBean valueAnnotationDemoBean() {
        return new ValueAnnotationDemoBean();
    }
}
