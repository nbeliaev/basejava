package ru.javawebinar.basejava.exception;

import java.text.Format;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super(String.format("Resume %s already exists", uuid), uuid);
    }
}
