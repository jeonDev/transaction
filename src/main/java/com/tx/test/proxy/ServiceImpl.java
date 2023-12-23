package com.tx.test.proxy;

import java.util.List;

public class ServiceImpl implements Service {

    private final Repository repository;

    public ServiceImpl(Repository repository) {
        this.repository = repository;
    }

    public void execute() {
        // Logic
        repository.create();
        repository.insert(1, 10000);
        if(1==1) throw new RuntimeException();
        repository.insert(2, 50000);

        List<Account> list = repository.select();
        for(Account account : list) {
            System.out.println(account.getAccountSeq() + " : " + account.getAmount());
        }

    }
}
