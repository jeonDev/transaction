package com.tx.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * DB Connection Pool Manager
 */
public class DBConnectionPoolManager implements DBConnectionPool {
    private final Queue<Transaction> connectionPool = new ConcurrentLinkedQueue<>();
    private static final int MAX_CONNECTION_POOL_SIZE = 10;

    public DBConnectionPoolManager(TransactionInfo transactionInfo, int connectionPoolSize) {
        for(int i = 0; i < connectionPoolSize; i++) {
            try {
                Transaction transaction = new TransactionManager(transactionInfo);
                Connection connect = transaction.connect();
                connect.setAutoCommit(false);
                if(!connectionPool.offer(transaction)) transaction.close(); // Connection Pool 세팅이 안될 경우 Connection Close
            } catch (SQLException e) {
                if(!connectionPool.isEmpty()) this.reset();
                throw new RuntimeException(e);
            }
        }
    }

    public DBConnectionPoolManager(TransactionInfo transactionInfo) {
        new DBConnectionPoolManager(transactionInfo, MAX_CONNECTION_POOL_SIZE);
    }

    @Override
    public Transaction get() {
        System.out.println("get : " + connectionPool.size());
        return connectionPool.poll();
    }

    @Override
    public void connectOff(Transaction transaction) {
        if(!connectionPool.offer(transaction)) transaction.close(); // Connection Pool 세팅이 안될 경우 Connection Close
    }

    @Override
    public void reset() {
        for(int i = 0; i != connectionPool.size();) {
            System.out.println("reset : " + connectionPool.size());
            Transaction transaction = connectionPool.poll();
            if(transaction != null) transaction.close();
        }
    }
}
