package spring.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class CalculatorServiceImpl implements CalculatorService {
    private static Logger log = LoggerFactory.getLogger(CalculatorServiceImpl.class);

    @Override
    public BigInteger doHeavyOperation(Long... numbers) {
        BigInteger sum = doHeavyAddition(numbers);

        if (sum.compareTo(BigInteger.ZERO) > 0) {
            doHeavyDivision(BigDecimal.ZERO, BigDecimal.ONE);
        }

        return sum;
    }

    private BigInteger doHeavyAddition(Long... numbers) {
        log.info("Addition started...");
        sleep(1000);

        BigInteger bigInteger = BigInteger.ZERO;

        for (Long number : numbers) {
            bigInteger = bigInteger.add(BigInteger.valueOf(number));
        }

        log.info("Addition finished...");
        return bigInteger;
    }

    @Async
    public void sendNotificationAboutAdditionResult(BigInteger sum) {
        log.info("Sending notification started...");
        sleep(3000);
        log.info("Result of sum is {}", sum);
        sleep(3000);
        log.info("Sending notification finished...");
    }

    private BigDecimal doHeavyDivision(BigDecimal dividend, BigDecimal divisor) {
        log.info("Division started...");
        log.info("Division finished...");
        return dividend.divide(divisor, RoundingMode.HALF_UP);
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
