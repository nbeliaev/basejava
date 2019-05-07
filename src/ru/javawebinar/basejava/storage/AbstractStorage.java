package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return getForIndex(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            updateForIndex(index, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            removeForIndex(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract int findIndex(String uuid);

    protected abstract Resume getForIndex(int index);

    protected abstract void updateForIndex(int index, Resume resume);

    protected abstract void removeForIndex(int index);
}
