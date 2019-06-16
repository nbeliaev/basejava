package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.Serialization.ObjectSerialization;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new ObjectPathStorage(STORAGE_DIRECTORY, new ObjectSerialization()));
    }
}