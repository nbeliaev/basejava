package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void remove(int index) {
        System.arraycopy(STORAGE, index + 1, STORAGE, index, currentSize - index - 1);
    }

    @Override
    protected void insert(int index, Resume resume) {
        if (currentSize == 0) {
            STORAGE[currentSize] = resume;
        } else {
            int currentIndex = -index - 1;
            System.arraycopy(STORAGE, currentIndex, STORAGE, currentIndex + 1, currentSize - currentIndex);
            STORAGE[currentIndex] = resume;
        }
    }

    @Override
    protected int findIndex(String uuid) {
        Resume r = new Resume(uuid);
        return Arrays.binarySearch(STORAGE, 0, currentSize, r);
    }

}
