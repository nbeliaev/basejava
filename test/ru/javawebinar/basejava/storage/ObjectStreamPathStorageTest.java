package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    private static final Path STORAGE_DIRECTORY;

    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIRECTORY));
    }

    static {
        STORAGE_DIRECTORY = Paths.get(System.getProperty("user.home") + File.separatorChar + "ResumeStorage");
        if (!Files.exists(STORAGE_DIRECTORY)) {
            try {
                Files.createDirectory(STORAGE_DIRECTORY);
            } catch (IOException e) {
                throw new StorageException("Couldn't create storage", STORAGE_DIRECTORY.getFileName().toString());
            }
        }
    }
}