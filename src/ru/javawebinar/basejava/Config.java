package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    private Path storageDir;

    public static Config getInstance() {
        return INSTANCE;
    }

    public Path getStoragePath() {
        return storageDir;
    }

    private Config() {
        File propsFile = new File("config/resumes.properties");
        try (InputStream input = new FileInputStream(propsFile)) {
            Properties props = new Properties();
            props.load(input);
            storageDir = Paths.get(props.getProperty("storage.dir"));
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid properties file " + propsFile.getAbsolutePath());
        }
    }

}
