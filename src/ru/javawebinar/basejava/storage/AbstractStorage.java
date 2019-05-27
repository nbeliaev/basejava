package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<T> implements Storage {

    public Resume get(String uuid) {
        T key = findKey(uuid);
        if (isExistKey(key)) {
            return getByKey(key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void update(Resume resume) {
        T key = findKey(resume.getUuid());
        if (isExistKey(key)) {
            updateByKey(key, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public void delete(String uuid) {
        T key = findKey(uuid);
        if (isExistKey(key)) {
            removeByKey(key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void save(Resume resume) {
        T key = findKey(resume.getUuid());
        if (!isExistKey(key)) {
            saveByKey(key, resume);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    public List<Resume> getAllSorted() {
        List<Resume> resumes = storageAsList();
        Collections.sort(resumes);
        return resumes;
    }

    protected abstract T findKey(String uuid);

    protected abstract boolean isExistKey(T key);

    protected abstract Resume getByKey(T key);

    protected abstract void updateByKey(T key, Resume resume);

    protected abstract void removeByKey(T key);

    protected abstract void saveByKey(T key, Resume resume);

    protected abstract List<Resume> storageAsList();
}
