package ru.javawebinar.basejava.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super(String.format("Resume %s already exists", uuid), uuid);
    }
}
