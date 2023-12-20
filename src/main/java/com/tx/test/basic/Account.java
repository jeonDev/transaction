package com.tx.test.basic;

public class Account {
    private int accountSeq;
    private int amount;

    public Account(int accountSeq, int amount) {
        this.accountSeq = accountSeq;
        this.amount = amount;
    }

    public int getAccountSeq() {
        return accountSeq;
    }

    public int getAmount() {
        return amount;
    }
}
