package com.tx.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface Transaction {
    Connection connect();
    PreparedStatement execute();
    ResultSet result();
    void close();
    Connection getConnection();
}
