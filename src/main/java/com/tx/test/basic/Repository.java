package com.tx.test.basic;

import com.tx.core.Transaction;
import com.tx.core.TransactionSync;
import com.tx.core.TransactionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    public void create() {
        TransactionSync transactionSync = TransactionUtils.getInstance();
        Transaction transaction = transactionSync.getTransaction();
        PreparedStatement ps = transaction.execute();
        ResultSet rs = transaction.result();
        Connection connection = transaction.getConnection();

        try {
            String sql = "CREATE TABLE ACCOUNT (ACCOUNT_SEQ NUMBER PRIMARY KEY , AMOUNT NUMBER)";
            ps = connection.prepareStatement(sql);
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
    public void insert(int accountSeq, int amount) {
        TransactionSync transactionSync = TransactionUtils.getInstance();
        Transaction transaction = transactionSync.getTransaction();
        PreparedStatement ps = transaction.execute();
        ResultSet rs = transaction.result();
        Connection connection = transaction.getConnection();

        try {
            String sql = "INSERT INTO ACCOUNT VALUES(?, ?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, accountSeq);
            ps.setInt(2, amount);
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public List<Account> select() {
        TransactionSync transactionSync = TransactionUtils.getInstance();
        Transaction transaction = transactionSync.getTransaction();
        PreparedStatement ps = transaction.execute();
        ResultSet rs = transaction.result();
        Connection connection = transaction.getConnection();
        List<Account> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM ACCOUNT";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                int accountSeq = rs.getInt(1);
                int amount = rs.getInt(2);
                list.add(new Account(accountSeq, amount));
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return list;
    }
}
