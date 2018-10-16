package spring.async;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface CalculatorService {
    BigInteger doHeavyOperation(Long... numbers);

    void sendNotificationAboutAdditionResult(BigInteger sum);
}
