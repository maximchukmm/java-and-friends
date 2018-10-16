package spring.async;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigInteger;


public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        CalculatorService calculatorService = context.getBean(CalculatorService.class);

        BigInteger sum1 = calculatorService.doHeavyOperation(1L, 2L, 3L);
        calculatorService.sendNotificationAboutAdditionResult(sum1);

        BigInteger sum2 = calculatorService.doHeavyOperation(4L, 5L, 6L);
        calculatorService.sendNotificationAboutAdditionResult(sum2);
    }
}
