package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<T> implements Storage {

    @Override
    public Resume get(String uuid) {
        T key = getExistKey(uuid);
        return doGet(key);
    }

    @Override
    public void update(Resume resume) {
        T key = getExistKey(resume.getUuid());
        doUpdate(key, resume);

    }

    @Override
    public void delete(String uuid) {
        T key = getExistKey(uuid);
        doDelete(key);
    }

    @Override
    public void save(Resume resume) {
        T key = getNotExistKey(resume.getUuid());
        doSave(key, resume);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = copyStorage();
        Collections.sort(resumes);
        return resumes;
    }

    private T getExistKey(String uuid) {
        T key = findKey(uuid);
        if (!isExistKey(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private T getNotExistKey(String uuid) {
        T key = findKey(uuid);
        if (isExistKey(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    protected abstract T findKey(String uuid);

    protected abstract boolean isExistKey(T key);

    protected abstract Resume doGet(T key);

    protected abstract void doUpdate(T key, Resume resume);

    protected abstract void doDelete(T key);

    protected abstract void doSave(T key, Resume resume);

    protected abstract List<Resume> copyStorage();
}
