package com.tx.core;

import java.sql.Connection;

public class TransactionSyncManager implements TransactionSync {

    private final ThreadLocal<Transaction> transactionThread = new ThreadLocal<>();
    private final TransactionInfo transactionInfo;

    public TransactionSyncManager(TransactionInfo transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    /**
     * Transaction Begin
     * 1. Connection Setting
     * 2. autoCommit : false
     */
    @Override
    public void begin() {
        try {
            TransactionManager transactionManager = new TransactionManager(transactionInfo);
            this.transactionThread.set(transactionManager);
            Connection connect = this.transactionThread.get().connect();
            connect.setAutoCommit(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Transaction Commit
     */
    @Override
    public void commit() {
        try {
            this.getTransaction().getConnection().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Transaction Rollback
     */
    @Override
    public void rollback() {
        try {
            this.getTransaction().getConnection().rollback();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Transaction Close
     */
    @Override
    public void close() {
        Transaction transaction = this.getTransaction();
        transaction.close();
        transactionThread.remove();
    }

    @Override
    public Transaction getTransaction() {
        return this.transactionThread != null ? this.transactionThread.get() : null;
    }
}
