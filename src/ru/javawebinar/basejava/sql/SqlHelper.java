package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExceptionUtil;
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
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T executeTransaction(SqlTransaction<T> executor) {
        try (final Connection cn = connection.getConnection()) {
            cn.setAutoCommit(false);
            try {
                final T response = executor.execute(cn);
                cn.commit();
                return response;
            } catch (SQLException e) {
                cn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
