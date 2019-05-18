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
    protected Resume getByKey(Object index) {
        return storage[(int) index];
    }

    @Override
    protected void updateByKey(Object index, Resume resume) {
        storage[(int) index] = resume;
    }

    @Override
    protected void removeByKey(Object index) {
        remove((int) index);
        storage[currentSize - 1] = null;
        currentSize--;
    }

    @Override
    protected void saveByKey(Object index, Resume resume) {
        if (currentSize < MAX_CAPACITY) {
            insert((int) index, resume);
            currentSize++;
        } else {
            throw new StorageException("Resume hasn't been added. Maximum storage size reached.", resume.getUuid());
        }
    }

    @Override
    protected boolean isValidKey(Object key) {
        return (int) key >= 0;
    }

    protected abstract void remove(int index);

    protected abstract void insert(int index, Resume resume);

}
