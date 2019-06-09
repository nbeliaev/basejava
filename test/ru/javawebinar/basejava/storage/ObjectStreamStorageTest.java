package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;

import java.io.File;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    private static final File STORAGE_DIRECTORY;

    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIRECTORY));
    }

    static {
        STORAGE_DIRECTORY = new File(System.getProperty("user.home") + File.separatorChar + "ResumeStorage");
        if (!STORAGE_DIRECTORY.exists()) {
            if (!STORAGE_DIRECTORY.mkdir()) {
                throw new StorageException("Couldn't create storage", STORAGE_DIRECTORY.getName());
            }
        }
    }
}