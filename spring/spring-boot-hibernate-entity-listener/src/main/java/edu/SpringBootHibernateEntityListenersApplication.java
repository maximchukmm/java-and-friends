package edu;

import edu.persistence.model.TwoNumbers;
import edu.persistence.repository.SumNumbersRepository;
import edu.persistence.repository.TwoNumbersRepository;
import edu.service.iface.CalculatorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Optional;

@SpringBootApplication
public class SpringBootHibernateEntityListenersApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootHibernateEntityListenersApplication.class);
    }

    @EventListener
    public void demo(ApplicationReadyEvent event) {
        CalculatorService calculatorService = event.getApplicationContext().getBean(CalculatorService.class);
        TwoNumbersRepository twoNumbersRepository = event.getApplicationContext().getBean(TwoNumbersRepository.class);

        Long twoNumbersId = calculatorService.createTwoNumbers(10, 20);
        Optional<TwoNumbers> twoNumbers = twoNumbersRepository.findById(twoNumbersId);
        twoNumbers.flatMap(tn -> {
            System.out.println(tn);
            return Optional.of(tn);
        });

        SumNumbersRepository sumNumbersRepository = event.getApplicationContext().getBean(SumNumbersRepository.class);
        sumNumbersRepository.findAll().forEach(System.out::println);
    }
}
