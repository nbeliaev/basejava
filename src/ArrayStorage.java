/**
 * Array based storage for Resumes
 */

import java.util.Arrays;

public class ArrayStorage {

    private Resume[] storage;
    private final int MAX_CAPACITY;
    private int currentSize;

    ArrayStorage() {
        MAX_CAPACITY = 10000;
        storage = new Resume[MAX_CAPACITY];
    }

    Resume get(String uuid) {
        Resume resume = null;

        for (int i = 0; i < currentSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                resume = storage[i];
                break;
            }
        }

        return resume;
    }

    void save(Resume r) {
        storage[currentSize] = r;
        currentSize++;
    }

    void delete(String uuid) {
        Resume resume = get(uuid);
        int i = Arrays.asList(storage).indexOf(resume);
        if (i == -1)
            return;

        storage[i] = null;
        currentSize--;

        Resume[] temp = new Resume[MAX_CAPACITY];
        int counter = 0;
        for (Resume r : storage) {

            if (r instanceof Resume) {
                temp[counter] = r;
                counter++;
            }

        }
        storage = temp;
    }

    int size() {
        return currentSize;
    }

    void clear() {

        for (int i = 0; i < currentSize; i++) {
            storage[i] = null;
        }
        currentSize = 0;

    }

    Resume[] getAll() {
        Resume[] resumes = new Resume[currentSize];
        for (int i = 0; i < currentSize; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

}
