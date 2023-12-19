package com.tx.basic.core;

import java.sql.*;

public class TransactionManager implements Transaction {

    private Connection c;
    private PreparedStatement ps;
    private ResultSet rs;

    private TransactionInfo transactionInfo;



    public TransactionManager(TransactionInfo transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    @Override
    public Connection getConnection() {
        System.out.println("TransactionManager Connect");
        try {
            Class.forName(transactionInfo.getDriver());
            c = DriverManager.getConnection(transactionInfo.getUrl(), transactionInfo.getId(), transactionInfo.getPassword());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return c;
    }

    @Override
    public PreparedStatement execute() {
        return null;
    }

    @Override
    public ResultSet result() {
        return null;
    }

    @Override
    public void close() {
        try {
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
