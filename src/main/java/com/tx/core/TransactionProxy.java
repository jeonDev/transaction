package com.tx.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TransactionProxy implements InvocationHandler {

    private final Object target;

    public TransactionProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TransactionSync transaction = TransactionUtils.getInstance();
        Object result = null;
        try {
            transaction.begin();
            System.out.println("Transaction Proxy Begin!");
            result = method.invoke(target, args);
            transaction.commit();
            System.out.println("Transaction Proxy Commit!");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Throwable targetException = e.getTargetException();
            if(targetException instanceof RuntimeException) {
                System.out.println("Transaction Proxy Runtime Exception!");
                transaction.rollback();
                System.out.println("Transaction Proxy Rollback!");
            }
            throw targetException;
        } finally {
            // TODO: 현재는 각 요청(Proxy) 마다 Connection을 관리하는데, Connection Pool로 관리를 하게 되면 객체 반환을 하지 않아도 됨.
            transaction.close();
            System.out.println("Transaction Proxy Close");
        }
        return result;
    }
}
