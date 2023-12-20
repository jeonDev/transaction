package com.tx.test.basic;

public class Execute {
    public static void main(String[] args) {
        Repository repository = new Repository();
        Service service = new Service(repository);
        service.execute();
    }
}
