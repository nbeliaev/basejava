package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.ResumeTestData;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    protected final Storage storage;
    static final Path STORAGE_DIRECTORY;
    private final static String UUID_1 = UUID.randomUUID().toString();
    private final static String UUID_2 = UUID.randomUUID().toString();
    private final static String UUID_3 = UUID.randomUUID().toString();
    private final static Resume RESUME_1 = ResumeTestData.getResume(UUID_1, "name1");
    private final static Resume RESUME_2 = ResumeTestData.getResume(UUID_2, "name2");
    private final static Resume RESUME_3 = ResumeTestData.getResume(UUID_3, "name3");

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    static {
        STORAGE_DIRECTORY = Config.getInstance().getStoragePath();
        if (!Files.exists(STORAGE_DIRECTORY)) {
            try {
                Files.createDirectory(STORAGE_DIRECTORY);
            } catch (IOException e) {
                throw new StorageException("Couldn't create storage", STORAGE_DIRECTORY.getFileName().toString());
            }
        }
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
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(new ArrayList<>(Arrays.asList(RESUME_1, RESUME_2, RESUME_3)), list);
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
        assertEquals(resume, storage.get(UUID_1));
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