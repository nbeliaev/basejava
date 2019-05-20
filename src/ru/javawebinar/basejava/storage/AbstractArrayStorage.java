package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

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
    protected Resume getByKey(Integer index) {
        return storage[index];
    }

    @Override
    protected void updateByKey(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected void removeByKey(Integer index) {
        remove(index);
        storage[currentSize - 1] = null;
        currentSize--;
    }

    @Override
    protected void saveByKey(Integer index, Resume resume) {
        if (currentSize < MAX_CAPACITY) {
            insert(index, resume);
            currentSize++;
        } else {
            throw new StorageException("Resume hasn't been added. Maximum storage size reached.", resume.getUuid());
        }
    }

    @Override
    protected boolean isValidKey(Integer key) {
        return key >= 0;
    }

    protected abstract void remove(int index);

    protected abstract void insert(int index, Resume resume);

}
