package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {

    private final Storage storage;
    private final String uuid1 = "uuid1";
    private final String uuid2 = "uuid2";
    private final String uuid3 = "uuid3";

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void init() {
        storage.clear();
        storage.save(new Resume(uuid1));
        storage.save(new Resume(uuid2));
        storage.save(new Resume(uuid3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void getAll() {
        Assert.assertEquals(3, storage.getAll().length);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void saveNotExist() {
        storage.save(new Resume());
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(uuid2));
    }

    @Test
    public void deleteFirst() {
        storage.delete(uuid1);
        Assert.assertEquals(2, storage.size());
    }

    @Test
    public void deleteLast() {
        storage.delete(uuid3);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void updateExist() {
        storage.update(new Resume(uuid3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume());
    }

    @Test
    public void getExist() {
        storage.get(uuid1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test()
    public void overflow() {
        for (int i = 0; i < 10_000; i++) {
            try {
                storage.save(new Resume());
            } catch (StorageException e) {
                Assert.fail();
            }
        }
    }

}