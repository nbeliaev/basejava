package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void remove(int i) {
        System.arraycopy(STORAGE, i + 1, STORAGE, i, currentSize - i - 1);
    }

    @Override
    protected void insert(Resume resume, int i) {
        if (currentSize == 0) {
            STORAGE[currentSize] = resume;
        } else {
            int index = -i - 1;
            Resume[] resumes = Arrays.copyOfRange(STORAGE, index, currentSize);
            STORAGE[index] = resume;
            System.arraycopy(resumes, 0, STORAGE, index + 1, resumes.length);
        }
    }

    protected int findIndex(String uuid) {
        Resume r = new Resume();
        r.setUuid(uuid);
        return Arrays.binarySearch(STORAGE, 0, currentSize, r);
    }

}
