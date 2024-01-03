package com.unrealdinnerbone.postgresslib;

import com.unrealdinnerbone.config.config.ConfigValue;
import com.unrealdinnerbone.config.api.ConfigCreator;

public class PostgresConfig {

    private final ConfigValue<String> host;
    private final ConfigValue<Integer> port;
    private final ConfigValue<String> db;
    private final ConfigValue<String> username;
    private final ConfigValue<String> password;


    public PostgresConfig(ConfigCreator creator) {
        this.host = creator.createString("host", "");
        this.port = creator.createInteger("port", 5432);
        this.db = creator.createString("database","");
        this.username = creator.createString("username", "");
        this.password = creator.createString("password", "");
    }

    public ConfigValue<Integer> getPort() {
        return port;
    }

    public ConfigValue<String> getDb() {
        return db;
    }

    public ConfigValue<String> getHost() {
        return host;
    }

    public ConfigValue<String> getPassword() {
        return password;
    }

    public ConfigValue<String> getUsername() {
        return username;
    }
}
