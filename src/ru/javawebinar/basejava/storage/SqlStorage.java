package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final ConnectionFactory CONNECTION;

    SqlStorage(String dbUrl, String dbUser, String dbPwd) {
        CONNECTION = () -> DriverManager.getConnection(dbUrl, dbUser, dbPwd);
    }

    @Override
    public Resume get(String uuid) {
        SqlHelper<Resume> sqlHelper = new SqlHelper<>(
                CONNECTION,
                "SELECT * FROM resume WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    final ResultSet resultSet = ps.executeQuery();
                    if (resultSet.next()) {
                        return new Resume(uuid, resultSet.getString("full_name"));
                    } else {
                        throw new NotExistStorageException(uuid);
                    }
                }
        );
        return sqlHelper.execute();
    }

    @Override
    public void update(Resume resume) {
        SqlHelper<Integer> sqlHelper = new SqlHelper<>(
                CONNECTION,
                "UPDATE resume SET uuid = ?, full_name = ? WHERE uuid = ?",
                ps -> {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                    ps.setString(3, resume.getUuid());
                    return ps.executeUpdate();
                }
        );
        final Integer updatedRowCount = sqlHelper.execute();
        if (updatedRowCount == 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        SqlHelper<Boolean> sqlHelper = new SqlHelper<>(
                CONNECTION,
                "INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                ps -> {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                    return ps.execute();
                }
        );
        sqlHelper.execute();
    }

    @Override
    public void delete(String uuid) {
        SqlHelper<Integer> sqlHelper = new SqlHelper<>(
                CONNECTION,
                "DELETE FROM resume WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    return ps.executeUpdate();
                }
        );
        final Integer updatedRowCount = sqlHelper.execute();
        if (updatedRowCount == 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public int size() {
        SqlHelper<Integer> sqlHelper = new SqlHelper<>(
                CONNECTION,
                "SELECT COUNT(uuid) FROM resume",
                ps -> {
                    final ResultSet resultSet = ps.executeQuery();
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    } else {
                        return 0;
                    }
                }
        );
        return sqlHelper.execute();
    }

    @Override
    public void clear() {
        SqlHelper<Boolean> sqlHelper = new SqlHelper<>(
                CONNECTION,
                "DELETE FROM resume",
                PreparedStatement::execute);
        sqlHelper.execute();
    }

    @Override
    public List<Resume> getAllSorted() {
        SqlHelper<List<Resume>> sqlHelper = new SqlHelper<>(
                CONNECTION,
                "SELECT * FROM resume ORDER BY uuid",
                ps -> {
                    List<Resume> resumes = new ArrayList<>();
                    final ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next()) {
                        Resume resume = new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name"));
                        resumes.add(resume);
                    }
                    return resumes;
                }
        );
        return sqlHelper.execute();
    }
}
