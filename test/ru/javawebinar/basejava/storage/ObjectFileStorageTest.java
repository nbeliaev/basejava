package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.Serialization.ObjectStreamSerialization;

public class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() {
        super(new ObjectFileStorage(STORAGE_DIRECTORY.toFile(), new ObjectStreamSerialization()));
    }

}