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
    private final SqlHelper sqlHelper;

    SqlStorage(String dbUrl, String dbUser, String dbPwd) {
        ConnectionFactory connection = () -> DriverManager.getConnection(dbUrl, dbUser, dbPwd);
        sqlHelper = new SqlHelper(connection);
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute(
                "SELECT * FROM resume WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    final ResultSet resultSet = ps.executeQuery();
                    if (resultSet.next()) {
                        return new Resume(uuid, resultSet.getString("full_name"));
                    } else {
                        throw new NotExistStorageException(uuid);
                    }
                });
    }

    @Override
    public void update(Resume resume) {
        final Integer updatedRowCount = sqlHelper.execute(
                "UPDATE resume SET uuid = ?, full_name = ? WHERE uuid = ?",
                ps -> {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                    ps.setString(3, resume.getUuid());
                    return ps.executeUpdate();
                });
        if (updatedRowCount == 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.execute(
                "INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                ps -> {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                    return ps.execute();
                }
        );
    }

    @Override
    public void delete(String uuid) {
        final Integer updatedRowCount = sqlHelper.execute(
                "DELETE FROM resume WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    return ps.executeUpdate();
                }
        );
        if (updatedRowCount == 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public int size() {
        return sqlHelper.execute(
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
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute(
                "SELECT * FROM resume ORDER BY full_name, uuid",
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
    }
}
