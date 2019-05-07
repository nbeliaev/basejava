package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {

    private final ArrayList<Resume> STORAGE = new ArrayList<>();

    @Override
    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        STORAGE.add(resume);
    }

    @Override
    public int size() {
        return STORAGE.size();
    }

    @Override
    public void clear() {
        STORAGE.clear();
    }

    @Override
    public Resume[] getAll() {
        return STORAGE.toArray(new Resume[STORAGE.size()]);
    }

    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < STORAGE.size(); i++) {
            if (STORAGE.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Resume getForIndex(int index) {
        return STORAGE.get(index);
    }

    @Override
    protected void updateForIndex(int index, Resume resume) {
        STORAGE.set(index, resume);
    }

    @Override
    protected void removeForIndex(int index) {
        STORAGE.remove(index);
    }
}
