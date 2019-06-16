package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.Serialization.XmlSerialization;

public class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new ObjectPathStorage(STORAGE_DIRECTORY, new XmlSerialization()));
    }
}