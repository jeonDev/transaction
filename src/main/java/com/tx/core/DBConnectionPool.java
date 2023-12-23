package com.tx.core;

/**
 * DB Connection Pool
 */
public interface DBConnectionPool {
    /**
     * Connection Pool 내에 Connection 객체 존재 시 꺼내기
     * @return
     */
    Transaction get();

    /**
     * Connection 객체 반환
     */
    void connectOff(Transaction transaction);

    /**
     * Connection Pool 초기화
     */
    void reset();
}
