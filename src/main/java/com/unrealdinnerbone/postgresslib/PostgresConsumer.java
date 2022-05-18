package com.unrealdinnerbone.postgresslib;

import com.unrealdinnerbone.unreallib.exception.ExceptionConsumer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PostgresConsumer extends ExceptionConsumer<PreparedStatement, SQLException> {}
