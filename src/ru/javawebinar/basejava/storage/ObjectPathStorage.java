package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Serialization.SerializationStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ObjectPathStorage extends AbstractStorage<Path> {

    private final Path directory;
    private final SerializationStrategy serialization;

    public ObjectPathStorage(Path directory, SerializationStrategy serialization) {
        Objects.requireNonNull(serialization, "serialization is required");
        Objects.requireNonNull(directory, "directory is required");
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(directory.getFileName() + " is not directory.");
        }
        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new StorageException("directory is not available for RW operations.", directory.getFileName().toString());
        }
        this.directory = directory;
        this.serialization = serialization;
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
            return serialization.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Couldn't read the resume", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            serialization.doWrite(new BufferedOutputStream(Files.newOutputStream(path)), resume);
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
            serialization.doWrite(new BufferedOutputStream(Files.newOutputStream(path)), resume);
        } catch (IOException e) {
            throw new StorageException("Couldn't create the new resume", path.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> copyStorage() {
        List<Resume> resumes = new ArrayList<>();
        getPathList("Couldn't read the storage").forEach(path -> {
            try {
                resumes.add(serialization.doRead(new BufferedInputStream(Files.newInputStream(path))));
            } catch (IOException e) {
                throw new StorageException("Couldn't read the storage", path.getFileName().toString(), e);
            }
        });
        return resumes;
    }

    @Override
    public int size() {
        return (int) getPathList("Couldn't count storage size").count();
    }

    @Override
    public void clear() {
        getPathList("Couldn't clear storage").forEach(this::doDelete);
    }

    private Stream<Path> getPathList(String msgException) {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException(msgException, null, e);
        }

    }

}
