package ru.javawebinar.basejava.storage;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class ListStorageTest extends AbstractArrayStorageTest{

    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    @Ignore
    @Test
    public void saveOverflow() {
        // do nothing
    }
}