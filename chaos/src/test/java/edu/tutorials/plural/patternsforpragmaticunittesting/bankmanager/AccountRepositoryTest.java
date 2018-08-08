package edu.tutorials.plural.patternsforpragmaticunittesting.bankmanager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class AccountRepositoryTest extends BaseTestClass {
    private AccountRepository accountRepository;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {new SimpleTransaction(0)}, {new SimpleTransaction(5)}, {new SimpleTransaction(10)},
            {new FeeTransaction(0, 0)}, {new FeeTransaction(5, 0)}, {new FeeTransaction(10, 0)}
        });
    }

    private Transaction transaction;

    public AccountRepositoryTest(Transaction transaction) {
        this.transaction = transaction;
    }

    @Before
    @Override
    public void setUp() {
        accountRepository = new AccountRepository();
    }

    @Test
    public void getBalance_WithNoTransactions_Returns0Balance() {
        int accountBalance = accountRepository.getBalance();

        int expectedBalance = 0;

        Assert.assertEquals(expectedBalance, accountBalance);
    }

    @Test
    public void processTransaction_FirstTransaction_StoredTransactionsContainOnlyThatOneTransaction() {
        accountRepository.processTransaction(transaction);
        int actualBalance = accountRepository.getBalance();

        List<Transaction> transactions = accountRepository.getTransactions();
        Assert.assertEquals(1, transactions.size());
        Assert.assertEquals(transaction.baseAmount, actualBalance);
    }

    @Test
    public void getBalance_WithOneTransaction_ReturnsTotalOfTransaction() {
        accountRepository.processTransaction(transaction);

        int totalOfTransaction = transaction.calculateTotalTransaction();

        int currentBalance = accountRepository.getBalance();

        Assert.assertEquals(currentBalance, totalOfTransaction);
    }
}
