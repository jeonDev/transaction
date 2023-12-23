package com.tx.core;

import java.sql.*;

public class TransactionManager implements Transaction {

    private Connection c;
    private TransactionInfo transactionInfo;

    public TransactionManager(TransactionInfo transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    @Override
    public Connection connect() {
        System.out.println("TransactionManager Connect");
        if(c != null) return this.getConnection();

        try {
            Class.forName(transactionInfo.getDriver());
            c = DriverManager.getConnection(transactionInfo.getUrl(), transactionInfo.getId(), transactionInfo.getPassword());
            return c;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            if(c != null) c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection() {
        return this.c;
    }
}
