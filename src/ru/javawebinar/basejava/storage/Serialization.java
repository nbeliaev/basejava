package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class Serialization {

    public void save(Storage storage, Resume resume) {
        storage.save(resume);
    }

    public Resume read(Storage storage, String uuid) {
        return storage.get(uuid);
    }
}
