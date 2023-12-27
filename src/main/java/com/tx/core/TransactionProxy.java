package com.tx.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * DB Transaction Proxy
 */
public class TransactionProxy implements InvocationHandler {

    private final Object target;

    public TransactionProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TransactionSync transactionSync = TransactionUtils.getInstance();
        Object result = null;
        try {
            transactionSync.begin();
            System.out.println("Transaction Proxy Begin!");
            result = method.invoke(target, args);
            transactionSync.commit();
            System.out.println("Transaction Proxy Commit!");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Throwable targetException = e.getTargetException();
            if(targetException instanceof RuntimeException) {
                System.out.println("Transaction Proxy Runtime Exception!");
                transactionSync.rollback();
                System.out.println("Transaction Proxy Rollback!");
            }
            throw targetException;
        } finally {
            // transactionSync.close();
            System.out.println("Transaction Proxy Close");
        }
        return result;
    }
}
