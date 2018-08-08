package edu.tutorials.plural.patternsforpragmaticunittesting.bankmanager;

import org.junit.Assert;
import org.junit.Test;

public class FeeTransactionTest extends TransactionTest {
    @Override
    Transaction getTransactionWith(int baseAmount) {
        return getTransactionWith(baseAmount, 0);
    }

    public Transaction getTransactionWith(int baseAmount, int fee) {
        return new FeeTransaction(baseAmount, fee);
    }

    @Test
    public void calculateTotalTransaction_AmountAndFeeProvided_ReturnsAmountMinusFee() {
        final int baseAmount = 100;
        final int fee = 5;
        FeeTransaction transaction = new FeeTransaction(baseAmount, fee);

        int actualTotalTransaction = transaction.calculateTotalTransaction();

        final int expectedTotalTransaction = baseAmount - fee;

        Assert.assertEquals(expectedTotalTransaction, actualTotalTransaction);
    }
}
