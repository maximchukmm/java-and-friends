package spring.severalservices.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan
public class ApplicationConfiguration {
    @Bean("mySuperMap")
    Map<Integer, String> mySuperMap() {
        Map<Integer, String> mySuperMap = new HashMap<>();
        mySuperMap.put(1, "one");
        mySuperMap.put(2, "two");
        mySuperMap.put(3, "three");

        return mySuperMap;
    }
}
