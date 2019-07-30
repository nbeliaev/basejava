package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper<T> {
    private final ConnectionFactory CONNECTION;
    private final String QUERY;
    private final SqlExecutor<T> EXECUTOR;

    public SqlHelper(ConnectionFactory connection, String query, SqlExecutor<T> executor) {
        CONNECTION = connection;
        QUERY = query;
        EXECUTOR = executor;
    }

    public T execute() {
        try (final Connection cn = CONNECTION.getConnection();
             final PreparedStatement ps = cn.prepareStatement(QUERY)) {
            return EXECUTOR.executeQuery(ps);
        } catch (SQLException e) {
            final String uniqueViolation = "23505";
            if (e.getSQLState().equals(uniqueViolation)) {
                throw new ExistStorageException(e.getMessage());
            } else {
                throw new StorageException(e);
            }
        }
    }
}
