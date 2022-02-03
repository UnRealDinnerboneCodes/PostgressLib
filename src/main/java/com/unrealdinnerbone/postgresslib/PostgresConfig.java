package com.unrealdinnerbone.postgresslib;

import com.unrealdinnerbone.config.IConfigCreator;
import com.unrealdinnerbone.config.config.IntegerConfig;
import com.unrealdinnerbone.config.config.StringConfig;

public class PostgresConfig {

    private StringConfig host;
    private IntegerConfig port;
    private StringConfig db;
    private StringConfig username;
    private StringConfig password;


    public PostgresConfig(IConfigCreator creator) {
        this.host = creator.createString("host", "localhost");
        this.port = creator.createInteger("port", 5432);
        this.db = creator.createString("database",null);
        this.username = creator.createString("username", null);
        this.password = creator.createString("password", null);
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
