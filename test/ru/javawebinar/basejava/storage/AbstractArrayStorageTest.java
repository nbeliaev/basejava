package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {

    private final Storage storage;
    private final static int MAX_CAPACITY = 10_000;
    private final static String UUID_1 = "uuid1";
    private final static String UUID_2 = "uuid2";
    private final static String UUID_3 = "uuid3";
    private final static Resume RESUME_1 = new Resume(UUID_1);
    private final static Resume RESUME_2 = new Resume(UUID_2);
    private final static Resume RESUME_3 = new Resume(UUID_3);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void init() {
        storage.clear();
        storage.save(AbstractArrayStorageTest.RESUME_1);
        storage.save(AbstractArrayStorageTest.RESUME_2);
        storage.save(AbstractArrayStorageTest.RESUME_3);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void getAll() {
        Resume[] expected = new Resume[]{
                AbstractArrayStorageTest.RESUME_1,
                AbstractArrayStorageTest.RESUME_2,
                AbstractArrayStorageTest.RESUME_3
        };
        assertArrayEquals(expected, storage.getAll());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        Resume resume = new Resume();
        storage.save(resume);
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(AbstractArrayStorageTest.UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(AbstractArrayStorageTest.UUID_1);
        storage.get(AbstractArrayStorageTest.UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void update() {
        storage.update(storage.get(AbstractArrayStorageTest.UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume());
    }

    @Test
    public void get() {
        assertEquals(AbstractArrayStorageTest.RESUME_1, storage.get(AbstractArrayStorageTest.UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        for (int i = 0; i < AbstractArrayStorageTest.MAX_CAPACITY; i++) {
            try {
                storage.save(new Resume());
            } catch (StorageException e) {
                fail();
            }
        }
        storage.save(new Resume());
    }

}