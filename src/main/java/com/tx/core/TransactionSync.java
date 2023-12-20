package com.tx.core;

public interface TransactionSync {
    void begin();
    void commit();
    void rollback();
    void close();
    Transaction getTransaction();
}
