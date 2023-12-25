package com.tx.test.connection_pool;

import com.tx.core.*;

import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.List;

public class Execute {

    public static void main(String[] args) throws SQLException {

        TransactionInfo transactionInfo = new TransactionInfo("org.h2.Driver", "jdbc:h2:mem:testdb", "sa", "");
        DBConnectionPool connectionPool = new DBConnectionPoolManager(transactionInfo, 3);
        TransactionUtils.setTransactionSync(new TransactionSyncManager(connectionPool));

        Repository repository = new Repository();
        Service service = new ServiceImpl(repository);
        TransactionProxy handler = new TransactionProxy(service);
        Service proxy = (Service) Proxy.newProxyInstance(Service.class.getClassLoader(), new Class[]{Service.class}, handler);

        try {
            proxy.execute();
        } catch (Exception e) {
            e.printStackTrace();
            List<Account> list = repository.select();
            if(list.isEmpty()) System.out.println("데이터 없음");
            else {
                for(Account account : list) {
                    System.out.println("데이터 : " + account.getAccountSeq() + " : " + account.getAmount());
                }
            }
        }



//        connectionPool.get();
//        connectionPool.connectOff();
        connectionPool.reset();
    }
}
