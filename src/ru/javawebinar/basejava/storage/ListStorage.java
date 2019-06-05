package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

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
    protected List<Resume> copyStorage() {
        return new ArrayList<>(storage);
    }

    @Override
    protected Integer findKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExistKey(Integer key) {
        return key >= 0;
    }

    @Override
    protected Resume doGet(Integer key) {
        return storage.get(key);
    }

    @Override
    protected void doUpdate(Integer key, Resume resume) {
        storage.set(key, resume);
    }

    @Override
    protected void doDelete(Integer key) {
        storage.remove((int) key);
    }

    @Override
    protected void doSave(Integer key, Resume resume) {
        storage.add(resume);
    }

}
