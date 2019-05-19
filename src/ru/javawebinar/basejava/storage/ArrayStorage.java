package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void remove(int index) {
        storage[index] = storage[currentSize - 1];
    }

    @Override
    protected void insert(int index, Resume resume) {
        storage[currentSize] = resume;
    }

    @Override
    protected Integer findKey(String uuid) {
        for (int i = 0; i < currentSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
