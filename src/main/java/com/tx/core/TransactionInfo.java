package com.tx.core;

public class TransactionInfo {
    private String driver;
    private String url;
    private String id;
    private String password;

    public TransactionInfo(String driver, String url, String id, String password) {
        this.driver = driver;
        this.url = url;
        this.id = id;
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

}
