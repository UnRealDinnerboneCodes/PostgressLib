package com.unrealdinnerbone.postgresslib;

import com.unrealdinnerbone.unreallib.StringUtils;
import com.unrealdinnerbone.unreallib.exception.ExceptionConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

public class PostgressHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostgressHandler.class);
    private final Connection postgres;

    public PostgressHandler(PostgresConfig postgresConfig) throws SQLException, ClassNotFoundException {
        String connectionString = StringUtils.replace("jdbc:postgresql://{0}:{1}/{2}?currentSchema={2}", postgresConfig.getHost().getValue(), postgresConfig.getPort().getValue(), postgresConfig.getDb().getValue());
        Class.forName("org.postgresql.Driver");
        postgres = DriverManager.getConnection(connectionString, postgresConfig.getUsername().getValue(), postgresConfig.getPassword().getValue());
    }

    public void executeUpdate(String quarry, ExceptionConsumer<PreparedStatement, SQLException> preparedStatementConsumer) {
        try {
            PreparedStatement preparedStatement = postgres.prepareStatement(quarry);
            preparedStatementConsumer.accept(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error while updating", e);
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
            LOGGER.error("Error while updating batch", throwables);
        }
    }

    public ResultSet getSet(String quarry) throws SQLException {
        return postgres.createStatement().executeQuery(quarry);
    }

    public ResultSet getSet(String quarry, ExceptionConsumer<PreparedStatement, SQLException> preparedStatementConsumer) {
        try {
            PreparedStatement preparedStatement = postgres.prepareStatement(quarry);
            preparedStatementConsumer.accept(preparedStatement);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            LOGGER.error("Error while updating", e);
            return null;
        }
    }
}
