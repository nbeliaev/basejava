package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.ResumeTestData;

import java.util.Arrays;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {

    protected final Storage storage;
    private final static String UUID_1 = "uuid1";
    private final static String UUID_2 = "uuid2";
    private final static String UUID_3 = "uuid3";
    private final static Resume RESUME_1 = new ResumeTestData(UUID_1, "name1").getResume();
    private final static Resume RESUME_2 = new ResumeTestData(UUID_2, "name2").getResume();
    private final static Resume RESUME_3 = new ResumeTestData(UUID_3, "name3").getResume();

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void init() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void getAllSorted() {
        assertEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_3), storage.getAllSorted());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        Resume resume = new Resume("dummy");
        storage.save(resume);
        assertEquals(resume, storage.get(resume.getUuid()));
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_1, "dummy");
        storage.update(resume);
        assertSame(resume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }
}