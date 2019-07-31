package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connection;

    public SqlHelper(ConnectionFactory connection) {
        this.connection = connection;
    }

    public <T> T execute(String query, SqlExecutor<T> executor) {
        try (final Connection cn = connection.getConnection();
             final PreparedStatement ps = cn.prepareStatement(query)) {
            return executor.executeQuery(ps);
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
