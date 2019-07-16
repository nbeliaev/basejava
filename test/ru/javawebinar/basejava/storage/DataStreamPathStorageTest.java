package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.Serialization.DataStreamSerialization;

public class DataStreamPathStorageTest extends AbstractStorageTest {

    public DataStreamPathStorageTest() {
        super(new ObjectPathStorage(STORAGE_DIRECTORY, new DataStreamSerialization()));
    }
}