package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    private final Path storageDir;
    private final String dbUrl;
    private final String dbUser;
    private final String dbPwd;


    public static Config getInstance() {
        return INSTANCE;
    }

    public Path getStoragePath() {
        return storageDir;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPwd() {
        return dbPwd;
    }

    private Config() {
        final File propsFile = getProperties();
        try (InputStream input = new FileInputStream(propsFile)) {
            Properties props = new Properties();
            props.load(input);
            storageDir = Paths.get(props.getProperty("storage.dir"));
            dbUrl = props.getProperty("db.url");
            dbUser = props.getProperty("db.user");
            dbPwd = props.getProperty("db.pwd");
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid properties file " + propsFile.getAbsolutePath());
        }
    }

    private File getProperties() {
        final URL resource = getClass().getResource("/config/resumes.properties");
        if (resource != null) {
            return new File(resource.getFile());
        } else {
            return new File("config/resumes.properties");
        }
    }
}
