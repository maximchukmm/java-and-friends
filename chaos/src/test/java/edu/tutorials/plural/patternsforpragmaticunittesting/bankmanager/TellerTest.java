package edu.tutorials.plural.patternsforpragmaticunittesting.bankmanager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

public class TellerTest extends BaseTestClass {
    private Teller teller;
    private AccountRepository accountRepository;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        //Arrange (AAA) or Given (Gherkin)
        accountRepository = mock(AccountRepository.class);
        teller = new Teller(accountRepository);
    }

    @Test
    //{method name}_{state of tests}_{expected result}
    public void checkBalance_RequestsTheAccountBalanceFromTheRepository() {
        int nonZeroBalance = 1;
        when(accountRepository.getBalance()).thenReturn(nonZeroBalance);
        //Act (AAA) or When (Gherkin)
        int accountBalance = teller.checkBalance();
        //Assert (AAA) or Then (Gherkin)
        Assert.assertEquals(nonZeroBalance, accountBalance);
    }

    @Test
    public void processTransaction_TransactionValueGiven_TellerSubmitGivenTransactionToTheRepository() {
        SimpleTransaction transaction = new SimpleTransaction(10);
        doAnswer(invocation -> {
            TimeUnit.MILLISECONDS.sleep(200);
            return null;
        }).when(accountRepository).processTransaction(transaction);
        //Act
        teller.processTransaction(transaction);
        //Assert
        verify(accountRepository, times(1)).processTransaction(transaction);
    }
}
