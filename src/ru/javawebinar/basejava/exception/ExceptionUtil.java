package ru.javawebinar.basejava.exception;

import java.sql.SQLException;

public class ExceptionUtil {
    public static StorageException convertException(SQLException e) {
        final String uniqueViolation = "23505";
        if (e.getSQLState().equals(uniqueViolation)) {
            return new ExistStorageException(e.getMessage());
        } else {
            return new StorageException(e);
        }
    }
}
