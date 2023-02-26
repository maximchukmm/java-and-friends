package edu.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

@EnableCaching
@SpringBootApplication
public class CacheDemoApp {
    private static final Logger log = LoggerFactory.getLogger(CacheDemoApp.class);

    public static void main(String[] args) {
        SpringApplication.run(CacheDemoApp.class);
    }

    @Component
    public static class AppRunner implements CommandLineRunner {
        private final WithoutCacheService nonCachedService;
        private final WithCacheService cachedService;
        private final CacheManager cacheManager;

        @Autowired
        public AppRunner(WithoutCacheService nonCachedService, WithCacheService cachedService, CacheManager cacheManager) {
            this.nonCachedService = nonCachedService;
            this.cachedService = cachedService;
            this.cacheManager = cacheManager;
        }

        @Override
        public void run(String... ignored) {
            for (int i = 0; i < 3; i++) {
                int result = nonCachedService.heavyCalculation(String.valueOf(i));
                log.info("Result: {}", result);
            }
            for (int i = 0; i < 3; i++) {
                int result = cachedService.heavyCalculation(String.valueOf(i));
                log.info("Result: {}", result);
            }
            for (int i = 0; i < 3; i++) {
                int result = cachedService.heavyCalculation(String.valueOf(i));
                log.info("Result: {}", result);
            }

            log.info("{}", cacheManager);
        }
    }

    @Component
    public static class WithoutCacheService implements HeavyCalculation {
        @Override
        public int heavyCalculation(String data) {
            return HeavyCalculation.super.heavyCalculation(data);
        }
    }

    @Component
    public static class WithCacheService implements HeavyCalculation {
        @Cacheable("heavy")
        @Override
        public int heavyCalculation(String data) {
            return HeavyCalculation.super.heavyCalculation(data);
        }
    }

    public interface HeavyCalculation {
        default int heavyCalculation(String data) {
            sleep(2);
            log.info("{} ends calculation with data: {}", this.getClass().getSimpleName(), data);
            return data.hashCode();
        }
    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
