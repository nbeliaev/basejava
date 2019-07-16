package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.Serialization.ObjectStreamSerialization;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new ObjectPathStorage(STORAGE_DIRECTORY, new ObjectStreamSerialization()));
    }
}