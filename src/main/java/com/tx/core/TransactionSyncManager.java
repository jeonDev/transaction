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
            Connection connection = this.getTransaction().getConnection();
            connection.setAutoCommit(false);
            return connection;
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
        Transaction transaction = transactionThread.get();
        // TODO: ThreadLocal에 값이 들어있음. 왜인지 확인 필요.
        if(transaction == null) {
            transaction = connectionPool.get();
            transactionThread.set(transaction);
        }
        return transaction;
    }
}
