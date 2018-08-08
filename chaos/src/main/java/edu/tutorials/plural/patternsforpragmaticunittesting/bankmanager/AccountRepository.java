package edu.tutorials.plural.patternsforpragmaticunittesting.bankmanager;

import java.util.ArrayList;
import java.util.List;

class AccountRepository {
    private List<Transaction> transactions = new ArrayList<>();

    void processTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    int getBalance() {
        return transactions.stream().mapToInt(Transaction::calculateTotalTransaction).sum();
    }

    List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }
}
