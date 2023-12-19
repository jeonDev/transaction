package com.tx.basic.core;

public class TransactionSyncManager implements TransactionSync {

    private ThreadLocal<Transaction> transactionThread = new ThreadLocal<>();

    @Override
    public void begin() {
        TransactionInfo transactionInfo = new TransactionInfo("org.h2.Driver", "jdbc:h2:mem:testdb", "sa", "");
        TransactionManager transactionManager = new TransactionManager(transactionInfo);
        transactionThread.set(transactionManager);
    }

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public void close() {
        Transaction transaction = transactionThread.get();
        transaction.close();
        transactionThread.remove();
    }
}
