package edu.tutorials.plural.patternsforpragmaticunittesting.bankmanager;

import org.junit.Assert;
import org.junit.Test;

abstract public class TransactionTest {
    abstract Transaction getTransactionWith(int baseAmount);

    @Test
    public void baseAmount_AmountPassedToConstructor_ReturnsSameAmount() {
        final int baseAmount = 5;
        Transaction transaction = getTransactionWith(baseAmount);

        Assert.assertEquals(baseAmount, transaction.baseAmount);
    }
}
