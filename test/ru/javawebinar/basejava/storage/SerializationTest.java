package ru.javawebinar.basejava.storage;

import org.junit.Test;
import ru.javawebinar.basejava.ResumeTestData;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class SerializationTest {
    private final static String UUID_1 = "uuid1";
    private final static Resume RESUME_1 = ResumeTestData.getResume(UUID_1, "name1");

    @Test
    public void fileRW() {
        final File directory = new File(System.getProperty("user.home") + File.separatorChar + "ResumeStorage");
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                throw new StorageException("Couldn't create storage", directory.getName());
            }
        }
        checkResult(new ObjectStreamStorage(directory));
    }

    @Test
    public void pathRW() {
        final Path directory = Paths.get(System.getProperty("user.home") + File.separatorChar + "ResumeStorage");
        if (!Files.exists(directory)) {
            try {
                Files.createDirectory(directory);
            } catch (IOException e) {
                throw new StorageException("Couldn't create storage", directory.getFileName().toString());
            }
        }
        checkResult(new ObjectStreamPathStorage(directory));
    }

    private void checkResult(Storage storage) {
        storage.clear();
        Serialization serialization = new Serialization();
        serialization.save(storage, RESUME_1);
        assertEquals(RESUME_1, serialization.read(storage, UUID_1));
    }

}