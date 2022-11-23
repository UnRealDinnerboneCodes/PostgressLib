package com.unrealdinnerbone.postgresslib;

import com.unrealdinnerbone.unreallib.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

public class PostgressHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostgressHandler.class);
    private final Connection postgres;

    public PostgressHandler(PostgresConfig postgresConfig) throws SQLException {
        String connectionString = StringUtils.replace("jdbc:postgresql://{0}:{1}/{2}?currentSchema={2}", postgresConfig.getHost().getValue(), postgresConfig.getPort().getValue(), postgresConfig.getDb().getValue());
        LOGGER.info("Connecting to postgresql: {}", connectionString);
        postgres = DriverManager.getConnection(connectionString, postgresConfig.getUsername().getValue(), postgresConfig.getPassword().getValue());
    }

    public void executeUpdate(String quarry, PostgresConsumer preparedStatementConsumer) {
        try(PreparedStatement preparedStatement = postgres.prepareStatement(quarry)) {
            preparedStatementConsumer.accept(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error while updating", e);
        }
    }

    public boolean executeBatchUpdate(String quarry, List<PostgresConsumer> statements) {
        try(PreparedStatement preparedStatement = postgres.prepareStatement(quarry)) {
            for(PostgresConsumer statement : statements) {
                statement.accept(preparedStatement);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            return true;
        }catch (SQLException e) {
            LOGGER.error("Error while updating batch", e);
            return false;
        }
    }

    public ResultSet getSet(String quarry) throws SQLException {
        return postgres.createStatement().executeQuery(quarry);
    }

    public ResultSet getSet(String quarry, PostgresConsumer preparedStatementConsumer) {
        try(PreparedStatement preparedStatement = postgres.prepareStatement(quarry)) {
            preparedStatementConsumer.accept(preparedStatement);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            LOGGER.error("Error while updating", e);
            return null;
        }
    }
}
