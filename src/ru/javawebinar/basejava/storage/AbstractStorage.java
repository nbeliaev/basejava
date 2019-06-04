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
        return getByKey(key);
    }

    @Override
    public void update(Resume resume) {
        T key = getExistKey(resume.getUuid());
        updateByKey(key, resume);

    }

    @Override
    public void delete(String uuid) {
        T key = getExistKey(uuid);
        removeByKey(key);
    }

    @Override
    public void save(Resume resume) {
        T key = getNotExistKey(resume.getUuid());
        saveByKey(key, resume);
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

    protected abstract Resume getByKey(T key);

    protected abstract void updateByKey(T key, Resume resume);

    protected abstract void removeByKey(T key);

    protected abstract void saveByKey(T key, Resume resume);

    protected abstract List<Resume> copyStorage();
}
