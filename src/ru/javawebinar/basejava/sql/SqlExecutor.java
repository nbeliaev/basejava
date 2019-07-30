package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlExecutor<T> {
    T executeQuery(PreparedStatement ps) throws SQLException;
}