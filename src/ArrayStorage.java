/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    private Resume[] storage;
    private final int MAX_CAPACITY;
    private int currentSize;

    ArrayStorage() {
        MAX_CAPACITY = 10000;
        storage = new Resume[MAX_CAPACITY];
    }

    Resume get(String uuid) {
        for (int i = 0; i < currentSize; i++)
            if (storage[i].uuid.equals(uuid))
                return storage[i];
        return null;
    }

    void save(Resume r) {
        storage[currentSize] = r;
        currentSize++;
    }

    void delete(String uuid) {
        for (int i = 0; i < currentSize; i++) {
            if (storage[i].uuid.equals(uuid)) {

                storage[i] = null;
                Resume[] temp = new Resume[MAX_CAPACITY];
                int counter = 0;
                for (int j = 0; j < currentSize; j++) {
                    if (storage[j] != null) {
                        temp[counter] = storage[j];
                        counter++;
                    }
                }
                storage = temp;
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
