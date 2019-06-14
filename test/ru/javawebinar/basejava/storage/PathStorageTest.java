package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.Serialization.ObjectSerialization;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY, new ObjectSerialization()));
    }
}