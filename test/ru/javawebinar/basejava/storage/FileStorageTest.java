package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.Serialization.ObjectSerialization;

public class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIRECTORY.toFile(), new ObjectSerialization()));
    }

}