package ru.javawebinar.basejava.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        ListStorageTest.class,
        MapStorageUUIDTest.class,
        MapStorageResumeTest.class,
        SortedArrayStorageTest.class,
        FileStorageTest.class,
        PathStorageTest.class
})
public class AllStorageTest {
}
