package com.tx.core;

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
    public Connection connect() {
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
        return ps;
    }

    @Override
    public ResultSet result() {
        return rs;
    }

    @Override
    public void close() {
        try {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(c  != null)  c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection() {
        return this.c;
    }
}
