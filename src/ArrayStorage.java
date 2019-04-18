/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    private Resume[] storage;
    private int currentSize;

    ArrayStorage() {
        storage = new Resume[10000];
    }

    Resume get(String uuid) {
        for (int i = 0; i < currentSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void save(Resume r) {
        storage[currentSize] = r;
        currentSize++;
    }

    void delete(String uuid) {
        for (int i = 0; i < currentSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                System.arraycopy(storage, i+1, storage, i, currentSize);
                currentSize--;
                break;
            }
        }
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
        System.arraycopy(storage, 0, resumes, 0, currentSize);
        return resumes;
    }

}
