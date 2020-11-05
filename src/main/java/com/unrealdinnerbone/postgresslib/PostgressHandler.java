package com.unrealdinnerbone.postgresslib;

import com.unrealdinnerbone.unreallib.ExceptionConsumer;
import com.unrealdinnerbone.unreallib.LoopUtils;
import com.unrealdinnerbone.unreallib.StringUtils;
import com.unrealdinnerbone.unreallib.TaskScheduler;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
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
            log.error("There was an error while executing update", e);
        }
    }

    public CompletableFuture<ResultSet> getSet(String quarry) {
        CompletableFuture<ResultSet> completableFuture = new CompletableFuture<>();
        TaskScheduler.handleTaskOnThread(() -> {
            try {
                completableFuture.complete(postgres.createStatement().executeQuery(quarry));
            }catch (SQLException e) {
                e.printStackTrace();
            }

        });
        return completableFuture;
    }

    public CompletableFuture<ResultSet> getSets(String quarry, ExceptionConsumer<ResultSet, SQLException> resultSetExceptionSuppler) {
        return getSet(quarry).whenComplete((resultSet, throwable) -> LoopUtils.loop(resultSet::next, () -> resultSetExceptionSuppler.accept(resultSet)));
    }
}
