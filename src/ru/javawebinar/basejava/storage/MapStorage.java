package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
    protected int findIndex(String uuid) {
        List<String> keys = new ArrayList<String>(storage.keySet());
        return keys.indexOf(uuid);
    }

    @Override
    protected Resume getByIndex(int index) {
        String key = getKeyByIndex(index);
        return storage.get(key);
    }

    @Override
    protected void updateByIndex(int index, Resume resume) {
        String key = getKeyByIndex(index);
        storage.replace(key, resume);
    }

    @Override
    protected void removeByIndex(int index) {
        String key = getKeyByIndex(index);
        storage.remove(key);
    }

    @Override
    protected void saveByIndex(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    private String getKeyByIndex(int index) {
        List<String> keys = new ArrayList<String>(storage.keySet());
        return keys.get(index);
    }
}
