package edu.tutorials.plural.patternsforpragmaticunittesting.bankmanager;

import org.junit.Assert;
import org.junit.Test;

public class SimpleTransactionTest extends TransactionTest {
    @Override
    public Transaction getTransactionWith(int baseAmount) {
        return new SimpleTransaction(baseAmount);
    }

    @Test
    public void calculateTotalTransaction_AmountProvided_ReturnsSameAmount() {
        final int baseAmount = 100;
        SimpleTransaction transaction = new SimpleTransaction(baseAmount);

        int totalTransaction = transaction.calculateTotalTransaction();

        Assert.assertEquals(baseAmount, totalTransaction);
    }
}