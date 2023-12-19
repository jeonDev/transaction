package com.tx.basic.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface Transaction {
    Connection getConnection();
    PreparedStatement execute();
    ResultSet result();
    void close();
}
