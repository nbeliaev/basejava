package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageResume extends AbstractStorage<Resume> {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> copyStorage() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected Resume findKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExistKey(Resume key) {
        return key != null;
    }

    @Override
    protected Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected void doUpdate(Resume key, Resume resume) {
        storage.replace(key.getUuid(), resume);
    }

    @Override
    protected void doDelete(Resume key) {
        storage.remove(key.getUuid());
    }

    @Override
    protected void doSave(Resume key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

}
