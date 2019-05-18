package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage<T> implements Storage {

    public Resume get(String uuid) {
        T key = findKey(uuid);
        if (isValidKey(key)) {
            return getByKey(key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void update(Resume resume) {
        T key = findKey(resume.getUuid());
        if (isValidKey(key)) {
            updateByKey(key, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public void delete(String uuid) {
        T key = findKey(uuid);
        if (isValidKey(key)) {
            removeByKey(key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void save(Resume resume) {
        T key = findKey(resume.getUuid());
        if (!isValidKey(key)) {
            saveByKey(key, resume);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    protected abstract T findKey(String uuid);

    protected abstract boolean isValidKey(T key);

    protected abstract Resume getByKey(T key);

    protected abstract void updateByKey(T key, Resume resume);

    protected abstract void removeByKey(T key);

    protected abstract void saveByKey(T key, Resume resume);
}
