package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> storage = new ArrayList<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    protected Object findKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isValidKey(Object key) {
        return (int) key >= 0;
    }

    @Override
    protected Resume getByKey(Object key) {
        return storage.get((int) key);
    }

    @Override
    protected void updateByKey(Object key, Resume resume) {
        storage.set((int) key, resume);
    }

    @Override
    protected void removeByKey(Object key) {
        storage.remove((int) key);
    }

    @Override
    protected void saveByKey(Object key, Resume resume) {
        storage.add(resume);
    }
}
