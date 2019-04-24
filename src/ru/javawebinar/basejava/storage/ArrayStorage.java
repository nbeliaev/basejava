package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage implements Storage {

    private int currentSize;
    private static final int MAX_CAPACITY = 10_000;
    private final Resume[] storage = new Resume[MAX_CAPACITY];

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("Resume hasn't been found.");
            return null;
        }
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("Resume hasn't been found.");
        }
    }

    public void save(Resume resume) {
        if (currentSize == MAX_CAPACITY) {
            System.out.println("Resume hasn't been added. Maximum storage size reached.");
            return;
        }

        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            System.out.println("Resume hasn't been added. Resume with such uuid exists.");
            return;
        }
        storage[currentSize] = resume;
        currentSize++;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[currentSize - 1];
            storage[currentSize - 1] = null;
            currentSize--;
        } else {
            System.out.println("Resume hasn't been found.");
        }
    }

    public int size() {
        return currentSize;
    }

    public void clear() {
        Arrays.fill(storage, 0, currentSize, null);
        currentSize = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, currentSize);
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < currentSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

}
