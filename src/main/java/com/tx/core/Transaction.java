package com.tx.core;

import java.sql.Connection;

public interface Transaction {
    Connection connect();
    void close();
    Connection getConnection();
}
