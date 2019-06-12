package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;

import java.io.File;

public class FileStorageTest extends AbstractStorageTest{

    private static final File STORAGE_DIRECTORY;

    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIRECTORY, new ObjectSerialization()));
    }

    static {
        STORAGE_DIRECTORY = new File("./" + File.separatorChar + "ResumeStorage");
        if (!STORAGE_DIRECTORY.exists()) {
            if (!STORAGE_DIRECTORY.mkdir()) {
                throw new StorageException("Couldn't create storage", STORAGE_DIRECTORY.getName());
            }
        }
    }
}