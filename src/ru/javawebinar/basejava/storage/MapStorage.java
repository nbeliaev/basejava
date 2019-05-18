package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> storage = new LinkedHashMap<>();

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
        return storage.values().toArray(new Resume[storage.size()]);
    }

    @Override
    protected Object findKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isValidKey(Object key) {
        Resume resume = storage.get(key);
        return resume != null;
    }

    @Override
    protected Resume getByKey(Object key) {
        return storage.get(key);
    }

    @Override
    protected void updateByKey(Object key, Resume resume) {
        storage.replace((String) key, resume);
    }

    @Override
    protected void removeByKey(Object key) {
        storage.remove(key);
    }

    @Override
    protected void saveByKey(Object key, Resume resume) {
        storage.put((String) key, resume);
    }
}
