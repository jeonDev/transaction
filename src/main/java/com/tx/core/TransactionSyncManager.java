package com.tx.core;

import java.sql.Connection;

/**
 * Transaction Synchronization Manager
 */
public class TransactionSyncManager implements TransactionSync {

    private final ThreadLocal<Transaction> transactionThread = new ThreadLocal<>();
    private final DBConnectionPool connectionPool;

    public TransactionSyncManager(DBConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * Transaction Begin
     * 1. Connection Setting
     * 2. autoCommit : false
     */
    @Override
    public Connection begin() {
        try {
            Transaction transaction = this.getTransaction();
            if(transaction == null) {
                transaction = connectionPool.get();
                transactionThread.set(transaction);
            }
            Connection connect = transaction.getConnection();
            return connect;
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
        this.connectionPool.connectOff(this.getTransaction()); // Connection Pool 반환
        transactionThread.remove();     // ThreadLocal 초기화
    }

    @Override
    public Transaction getTransaction() {
        return transactionThread.get();
    }
}
