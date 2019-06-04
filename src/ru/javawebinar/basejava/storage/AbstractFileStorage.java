package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private final File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory is required");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getName() + " is not directory.");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new StorageException("directory is not available for RW operations.", directory.getName());
        }
        this.directory = directory;
    }

    @Override
    protected File findKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExistKey(File file) {
        return file.exists();
    }

    @Override
    protected Resume getByKey(File file) {
        try {
            return readResume(file);

        } catch (IOException e) {
            throw new StorageException("can not read the resume", file.getName(), e);
        }
    }

    @Override
    protected void updateByKey(File file, Resume resume) {
        try {
            writeResume(file, resume);
        } catch (IOException e) {
            throw new StorageException("can not update the resume", file.getName(), e);
        }
    }

    @Override
    protected void removeByKey(File file) {
        file.delete();
    }

    @Override
    protected void saveByKey(File file, Resume resume) {
        try {
            file.createNewFile();
            writeResume(file, resume);
        } catch (IOException e) {
            throw new StorageException("can not create the new resume", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> copyStorage() {
        final File[] files = directory.listFiles();
        List<Resume> resumes = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                try {
                    resumes.add(readResume(file));
                } catch (IOException e) {
                    throw new StorageException("can not read the resume", file.getName(), e);
                }
            }
        }
        return resumes;
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.listFiles()).length;
    }

    @Override
    public void clear() {
        final File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    protected abstract void writeResume(File file, Resume resume) throws IOException;

    protected abstract Resume readResume(File file) throws IOException;
}
