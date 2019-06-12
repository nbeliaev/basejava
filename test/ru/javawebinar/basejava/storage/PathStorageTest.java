package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathStorageTest extends AbstractStorageTest {

    private static final Path STORAGE_DIRECTORY;

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY, new ObjectSerialization()));
    }

    static {
        STORAGE_DIRECTORY = Paths.get("./" + File.separatorChar + "ResumeStorage");
        if (!Files.exists(STORAGE_DIRECTORY)) {
            try {
                Files.createDirectory(STORAGE_DIRECTORY);
            } catch (IOException e) {
                throw new StorageException("Couldn't create storage", STORAGE_DIRECTORY.getFileName().toString());
            }
        }
    }
}