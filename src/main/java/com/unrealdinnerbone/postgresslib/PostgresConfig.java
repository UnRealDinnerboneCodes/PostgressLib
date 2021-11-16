package com.unrealdinnerbone.postgresslib;


import com.unrealdinnerbone.config.api.IConfig;

public class PostgresConfig implements IConfig {

    private String host;
    private String port;
    private String db;
    private String username;
    private String password;

    @Override
    public String getName() {
        return "postgres";
    }

    @Override
    public String getFolderName() {
        return "config";
    }

    public String getDb() {
        return db;
    }

    public String getHost() {
        return host;
    }

    public String getPassword() {
        return password;
    }

    public String getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }
}
