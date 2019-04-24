package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public interface Storage {

    Resume get(String uuid);

    void update(Resume resume);

    void save(Resume resume);

    public void delete(String uuid);

    int size();

    void clear();

    Resume[] getAll();

}
