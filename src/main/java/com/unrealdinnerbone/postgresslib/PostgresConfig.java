package com.unrealdinnerbone.postgresslib;

import com.unrealdinnerbone.config.api.IConfig;
import lombok.Getter;

@Getter
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
}
