package com.tx.core;

/**
 * DB Connection Info
 */
public class TransactionInfo {
    private String driver;      // DB Driver Class Info
    private String url;         // DB Connection URL
    private String id;          // DB Connection ID
    private String password;    // DB Connection Password

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
