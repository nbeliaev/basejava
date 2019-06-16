package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.Serialization.ObjectSerialization;

public class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() {
        super(new ObjectFileStorage(STORAGE_DIRECTORY.toFile(), new ObjectSerialization()));
    }

}