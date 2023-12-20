package com.tx.core;

public class TransactionUtils {
    private static TransactionSync transactionSync;

    private TransactionUtils() {
        transactionSync = new TransactionSyncManager(
                new TransactionInfo("org.h2.Driver", "jdbc:h2:mem:testdb", "sa", ""));
    }

    public static TransactionSync getInstance() {
        if (transactionSync != null) {
            return transactionSync;
        }
        return new TransactionUtils().getTransactionSync();
    }

    public TransactionSync getTransactionSync() {
        return transactionSync;
    }
}
