package com.tx.core;

import java.sql.Connection;

public interface TransactionSync {
    Connection begin();
    void commit();
    void rollback();
    void close();
    Transaction getTransaction();
}
