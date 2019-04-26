package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected int findIndex(String uuid) {
        Resume r = new Resume();
        r.setUuid(uuid);
        return Arrays.binarySearch(STORAGE, 0, currentSize, r);
    }

    @Override
    protected void sort(int i) {
        if (currentSize == 1) {
            return;
        }
        int index = -i - 1;
        Resume[] resumes = Arrays.copyOfRange(STORAGE, index, currentSize - 1);
        STORAGE[index] = STORAGE[currentSize - 1];
        System.arraycopy(resumes, 0, STORAGE, index + 1, resumes.length);

    }
}
