package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.Serialization.JsonSerialization;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new ObjectPathStorage(STORAGE_DIRECTORY, new JsonSerialization()));
    }
}