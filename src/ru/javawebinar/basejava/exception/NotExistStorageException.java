package ru.javawebinar.basejava.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super(String.format("Resume %s doesn't exist", uuid), uuid);
    }
}
