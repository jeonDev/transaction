package com.tx.test.proxy;

import com.tx.core.TransactionProxy;

import java.lang.reflect.Proxy;

public class Execute {
    public static void main(String[] args) {
        Repository repository = new Repository();
        Service service = new ServiceImpl(repository);
        TransactionProxy handler = new TransactionProxy(service);
        Service proxy = (Service) Proxy.newProxyInstance(Service.class.getClassLoader(), new Class[]{Service.class}, handler);

        try {
            proxy.execute();
        } catch (Exception e) {
            System.out.println("1");
        }
    }
}
