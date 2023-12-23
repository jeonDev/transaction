package com.tx.core;

public class TransactionUtils {
    private static TransactionSync transactionSync;

    private TransactionUtils() {
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

    public static void setTransactionSync(TransactionSync transactionSync) {
        TransactionUtils.transactionSync = transactionSync;
    }
}
