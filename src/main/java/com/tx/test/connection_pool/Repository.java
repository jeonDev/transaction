package com.tx.test.connection_pool;

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

    public void create() throws SQLException {
        TransactionSync transactionSync = TransactionUtils.getInstance();
        Connection connection = transactionSync.getTransaction().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "CREATE TABLE ACCOUNT (ACCOUNT_SEQ NUMBER PRIMARY KEY , AMOUNT NUMBER)";
            ps = connection.prepareStatement(sql);
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            // transactionSync.close();
        }
    }
    public void insert(int accountSeq, int amount) throws SQLException {
        TransactionSync transactionSync = TransactionUtils.getInstance();
        Connection connection = transactionSync.getTransaction().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "INSERT INTO ACCOUNT VALUES(?, ?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, accountSeq);
            ps.setInt(2, amount);
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            // transactionSync.close();
        }
    }

    public List<Account> select() throws SQLException {
        TransactionSync transactionSync = TransactionUtils.getInstance();
        Connection connection = transactionSync.getTransaction().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
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
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            // transactionSync.close();
        }
        return list;
    }
}
