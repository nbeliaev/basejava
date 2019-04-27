package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void remove(int i) {
        STORAGE[i] = STORAGE[currentSize - 1];
        STORAGE[currentSize - 1] = null;
    }

    @Override
    protected void insert(Resume resume, int index) {
        STORAGE[currentSize] = resume;
    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < currentSize; i++) {
            if (STORAGE[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

}
