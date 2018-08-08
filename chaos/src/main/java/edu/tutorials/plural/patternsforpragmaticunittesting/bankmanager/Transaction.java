package edu.tutorials.plural.patternsforpragmaticunittesting.bankmanager;

abstract class Transaction {
    protected int baseAmount;

    protected Transaction(int baseAmount) {
        this.baseAmount = baseAmount;
    }

    abstract int calculateTotalTransaction();

    int getBaseAmount() {
        return baseAmount;
    }
}
