package com.unrealdinnerbone.postgresslib;

import com.unrealdinnerbone.unreallib.ExceptionConsumer;
import com.unrealdinnerbone.unreallib.StringUtils;
import com.unrealdinnerbone.unreallib.TaskScheduler;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class PostgressHandler {

    private final Connection postgres;

    public PostgressHandler(PostgresConfig postgresConfig) throws SQLException, ClassNotFoundException {
        String connectionString = StringUtils.replace("jdbc:postgresql://{0}:{1}/{2}?currentSchema={2}", postgresConfig.getHost(), postgresConfig.getPort(), postgresConfig.getDb());
        Class.forName("org.postgresql.Driver");
        postgres = DriverManager.getConnection(connectionString, postgresConfig.getUsername(), postgresConfig.getPassword());
    }

    public void executeUpdate(String quarry, ExceptionConsumer<PreparedStatement, SQLException> preparedStatementConsumer) {
        try {
            PreparedStatement preparedStatement = postgres.prepareStatement(quarry);
            preparedStatementConsumer.accept(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//            log.error("There was an error while executing update", e);
        }
    }

    public void executeBatchUpdate(String quarry, List<ExceptionConsumer<PreparedStatement, SQLException>> statemetns) {
        try {
            PreparedStatement preparedStatement = postgres.prepareStatement(quarry);
            for(ExceptionConsumer<PreparedStatement, SQLException> statemetn : statemetns) {
                statemetn.accept(preparedStatement);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ResultSet getSet(String quarry) throws SQLException {
        return postgres.createStatement().executeQuery(quarry);
    }


    public static record SneakStatement(PreparedStatement preparedStatement) {

    }
}
