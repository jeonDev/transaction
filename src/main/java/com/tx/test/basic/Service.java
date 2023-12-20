package com.tx.test.basic;

import com.tx.core.TransactionSync;
import com.tx.core.TransactionUtils;

import java.util.List;

public class Service {

    private final Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public void execute() {
        TransactionSync transactionSync = TransactionUtils.getInstance();
        transactionSync.begin();

        try {
            // Logic
            repository.create();
            repository.insert(1, 10000);
            if(1==1) throw new Exception();
            repository.insert(2, 50000);

            List<Account> list = repository.select();
            for(Account account : list) {
                System.out.println(account.getAccountSeq() + " : " + account.getAmount());
            }

            transactionSync.commit();
        } catch (Exception e) {
            System.out.println("error" + e);
            transactionSync.rollback();
        } finally {
            transactionSync.close();
        }

    }

}
