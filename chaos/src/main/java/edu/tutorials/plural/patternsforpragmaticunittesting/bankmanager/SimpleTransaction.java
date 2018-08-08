package edu.tutorials.plural.patternsforpragmaticunittesting.bankmanager;

class SimpleTransaction extends Transaction {
    SimpleTransaction(int baseAmount) {
        super(baseAmount);
    }

    @Override
    int calculateTotalTransaction() {
        return baseAmount;
    }
}
