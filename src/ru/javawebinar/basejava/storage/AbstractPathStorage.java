package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    private final Path directory;

    public AbstractPathStorage(Path directory) {
        Objects.requireNonNull(directory, "directory is required");
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(directory.getFileName() + " is not directory.");
        }
        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new StorageException("directory is not available for RW operations.", directory.getFileName().toString());
        }
        this.directory = directory;
    }

    @Override
    protected Path findKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected boolean isExistKey(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Couldn't read the resume", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            doWrite(new BufferedOutputStream(Files.newOutputStream(path)), resume);
        } catch (IOException e) {
            throw new StorageException("Couldn't update the resume", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't delete the file", path.getFileName().toString());
        }
    }

    @Override
    protected void doSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
            doWrite(new BufferedOutputStream(Files.newOutputStream(path)), resume);
        } catch (IOException e) {
            throw new StorageException("Couldn't create the new resume", path.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> copyStorage() {
        List<Resume> resumes = new ArrayList<>();
        try {
            Files.list(directory).forEach(path -> {
                try {
                    resumes.add(doRead(new BufferedInputStream(Files.newInputStream(path))));
                } catch (IOException e) {
                    throw new StorageException("Couldn't read the storage", path.getFileName().toString(), e);
                }
            });
        } catch (IOException e) {
            throw new StorageException("Couldn't read the storage", null, e);
        }
        return resumes;
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Couldn't count storage size", null, e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Couldn't clear storage", null, e);
        }
    }

    protected abstract void doWrite(OutputStream outputStream, Resume resume) throws IOException;

    protected abstract Resume doRead(InputStream inputStream) throws IOException;
}
