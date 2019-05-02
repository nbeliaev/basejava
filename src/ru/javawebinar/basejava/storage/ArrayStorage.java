package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void remove(int index) {
        STORAGE[index] = STORAGE[currentSize - 1];
    }

    @Override
    protected void insert(int index, Resume resume) {
        STORAGE[currentSize] = resume;
    }

    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < currentSize; i++) {
            if (STORAGE[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

}
