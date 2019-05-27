package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapStorageUUID extends AbstractStorage<String> {

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
    protected List<Resume> storageAsList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected String findKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExistKey(String key) {
        return storage.containsKey(key);
    }

    @Override
    protected Resume getByKey(String key) {
        return storage.get(key);
    }

    @Override
    protected void updateByKey(String key, Resume resume) {
        storage.replace(key, resume);
    }

    @Override
    protected void removeByKey(String key) {
        storage.remove(key);
    }

    @Override
    protected void saveByKey(String key, Resume resume) {
        storage.put(key, resume);
    }

}
