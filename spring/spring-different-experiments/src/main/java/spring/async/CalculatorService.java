package spring.async;

import java.math.BigInteger;

public interface CalculatorService {
    BigInteger doHeavyOperation(Long... numbers);

    void sendNotificationAboutAdditionResult(BigInteger sum);
}
