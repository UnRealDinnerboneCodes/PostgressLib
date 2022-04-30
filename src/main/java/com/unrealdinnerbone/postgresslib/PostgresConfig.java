package com.unrealdinnerbone.postgresslib;

import com.unrealdinnerbone.config.IConfigCreator;
import com.unrealdinnerbone.config.config.IntegerConfig;
import com.unrealdinnerbone.config.config.StringConfig;

public class PostgresConfig {

    private final StringConfig host;
    private final IntegerConfig port;
    private final StringConfig db;
    private final StringConfig username;
    private final StringConfig password;


    public PostgresConfig(IConfigCreator creator) {
        this.host = creator.createString("host", "");
        this.port = creator.createInteger("port", 5432);
        this.db = creator.createString("database","");
        this.username = creator.createString("username", "");
        this.password = creator.createString("password", "");
    }

    public IntegerConfig getPort() {
        return port;
    }

    public StringConfig getDb() {
        return db;
    }

    public StringConfig getHost() {
        return host;
    }

    public StringConfig getPassword() {
        return password;
    }

    public StringConfig getUsername() {
        return username;
    }
}
