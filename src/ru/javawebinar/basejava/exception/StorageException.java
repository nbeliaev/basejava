package ru.javawebinar.basejava.exception;

public class StorageException extends RuntimeException {

    private final String uuid;

    public StorageException(String msg, String uuid) {
        super(msg);
        this.uuid = uuid;
    }

    public StorageException(String msg, String uuid, Exception e) {
        super(msg, e);
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
