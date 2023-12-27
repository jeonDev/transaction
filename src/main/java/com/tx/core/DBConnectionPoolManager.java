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
    private static final int RETRY_CONNECT = 100;

    public DBConnectionPoolManager(TransactionInfo transactionInfo, int connectionPoolSize) {
        for(int i = 0; i < connectionPoolSize; i++) {
            Transaction transaction = new TransactionManager(transactionInfo);
            transaction.connect();
            if(!connectionPool.offer(transaction)) transaction.close(); // Connection Pool 세팅이 안될 경우 Connection Close
        }
    }

    public DBConnectionPoolManager(TransactionInfo transactionInfo) {
        new DBConnectionPoolManager(transactionInfo, MAX_CONNECTION_POOL_SIZE);
    }

    @Override
    public Transaction get() {
        System.out.println("get : " + connectionPool.size());
        if(!connectionPool.isEmpty())
            return connectionPool.poll();

        // Connection Pool 에 Transaction 이 존재 하지 않을 경우 재 시도
        // TODO: 최대 연결 횟수 필요.
        try {
            Thread.sleep(RETRY_CONNECT);
            return this.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void connectOff(Transaction transaction) {
        if(transaction != null) {
            try {
                transaction.getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // Connection Pool 세팅이 안될 경우 Connection Close
        if(!connectionPool.offer(transaction)) {
            transaction.close();
        }
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
