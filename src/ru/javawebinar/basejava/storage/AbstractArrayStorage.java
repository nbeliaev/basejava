package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.*;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected int currentSize;
    protected static final int MAX_CAPACITY = 10_000;
    protected final Resume[] STORAGE = new Resume[MAX_CAPACITY];

    public int size() {
        return currentSize;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(STORAGE, currentSize);
    }

    public void clear() {
        Arrays.fill(STORAGE, 0, currentSize, null);
        currentSize = 0;
    }

    public void save(Resume resume) {
        if (currentSize == MAX_CAPACITY) {
            throw new StorageException("Resume hasn't been added. Maximum storage size reached.", resume.getUuid());
        }

        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        insert(index, resume);
        currentSize++;
    }

    @Override
    protected Resume getForIndex(int index) {
        return STORAGE[index];
    }

    @Override
    protected void updateForIndex(int index, Resume resume) {
        STORAGE[index] = resume;
    }

    @Override
    protected void removeForIndex(int index) {
        remove(index);
        STORAGE[currentSize - 1] = null;
        currentSize--;
    }

    protected abstract void remove(int index);

    protected abstract void insert(int index, Resume resume);

}
