package com.unrealdinnerbone.postgresslib;

import java.sql.*;
import java.util.List;

public interface IPostgresHandler {

    boolean executeUpdate(String quarry, PostgresConsumer preparedStatementConsumer);

    boolean executeBatchUpdate(String quarry, List<PostgresConsumer> statements);

    ResultSet getSet(String quarry) throws SQLException;

    ResultSet getSet(String quarry, PostgresConsumer preparedStatementConsumer);

    Array createArray(String type, Object[] values) throws SQLException;

}
