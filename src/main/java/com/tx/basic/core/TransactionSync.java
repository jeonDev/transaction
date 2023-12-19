package com.tx.basic.core;

public interface TransactionSync {
    void begin();
    void commit();
    void rollback();
    void close();
}
