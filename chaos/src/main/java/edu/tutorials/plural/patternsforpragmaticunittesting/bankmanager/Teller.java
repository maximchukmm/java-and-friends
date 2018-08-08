package edu.tutorials.plural.patternsforpragmaticunittesting.bankmanager;

import java.io.IOException;

class Teller {
    private AccountRepository accountRepository;

    Teller(AccountRepository accountRepository) {
        if (accountRepository == null)
            throw new NullPointerException("accountRepository");
        this.accountRepository = accountRepository;
    }

    int checkBalance() {
        try {
            Logging.writeLine("Checking the user's balance.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accountRepository.getBalance();
    }

    void processTransaction(Transaction transaction) {
        System.out.println("teller.processTransaction");
        new Thread(() -> {
            try {
                Thread.sleep(100);
                Logging.writeLine("Processing a transaction of $" + transaction.calculateTotalTransaction());
            } catch (IOException | InterruptedException | IllegalArgumentException e) {
                e.printStackTrace();
            }
            accountRepository.processTransaction(transaction);
        }).start();
    }
}
