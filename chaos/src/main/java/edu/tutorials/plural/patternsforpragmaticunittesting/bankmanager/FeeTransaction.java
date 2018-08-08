package edu.tutorials.plural.patternsforpragmaticunittesting.bankmanager;

class FeeTransaction extends Transaction {
    private final int fee;

    FeeTransaction(int baseAmount, int fee) {
        super(baseAmount);
        this.fee = fee;
    }

    @Override
    int calculateTotalTransaction() {
        return baseAmount - fee;
    }
}
