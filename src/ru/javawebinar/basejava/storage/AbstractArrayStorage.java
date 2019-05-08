package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected int currentSize;
    protected static final int MAX_CAPACITY = 10_000;
    protected Resume[] storage = new Resume[MAX_CAPACITY];

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, currentSize, null);
        currentSize = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, currentSize);
    }

    @Override
    protected Resume getByIndex(int index) {
        return storage[index];
    }

    @Override
    protected void updateByIndex(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected void removeByIndex(int index) {
        remove(index);
        storage[currentSize - 1] = null;
        currentSize--;
    }

    @Override
    protected void saveByIndex(int index, Resume resume) {
        if (currentSize < MAX_CAPACITY) {
            insert(index, resume);
            currentSize++;
        } else {
            throw new StorageException("Resume hasn't been added. Maximum storage size reached.", resume.getUuid());
        }
    }

    protected abstract void remove(int index);

    protected abstract void insert(int index, Resume resume);

}
