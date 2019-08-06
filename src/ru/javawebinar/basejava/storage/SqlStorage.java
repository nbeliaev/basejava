package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    SqlStorage(String dbUrl, String dbUser, String dbPwd) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPwd));
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute(
                "SELECT r.uuid, r.full_name, COALESCE(c.type, '') AS type, c.value\n" +
                        "FROM resume AS r\n" +
                        "   LEFT JOIN contact c ON r.uuid = c.resume_uuid\n" +
                        "WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    final ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }

                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(rs, resume);
                    } while (rs.next());
                    return resume;
                });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.executeTransaction(cn -> {
            try (final PreparedStatement ps = cn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                final int updatedRowCount = ps.executeUpdate();
                if (updatedRowCount == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            try (final PreparedStatement ps = cn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, resume.getUuid());
                ps.executeUpdate();
            }
            saveContacts(cn, resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.executeTransaction(cn -> {
                    try (PreparedStatement ps = cn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    saveContacts(cn, resume);
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute(
                "DELETE FROM resume WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    final int updatedRowCount = ps.executeUpdate();
                    if (updatedRowCount == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return updatedRowCount;
                }
        );
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
                "SELECT r.uuid, r.full_name, COALESCE(c.type, '') AS type, c.value\n" +
                        "FROM resume AS r\n" +
                        "   LEFT JOIN contact c ON r.uuid = c.resume_uuid\n" +
                        "ORDER BY r.full_name, r.uuid",
                ps -> {
                    List<Resume> resumes = new ArrayList<>();
                    final ResultSet rs = ps.executeQuery();
                    String previousUuid = "";
                    Resume resume = new Resume();
                    while (rs.next()) {
                        final String currentUuid = rs.getString("uuid").trim();
                        final boolean uuidChanged = !previousUuid.equals(currentUuid);
                        if (uuidChanged && !previousUuid.isEmpty()) {
                            resumes.add(resume);
                        }
                        if (uuidChanged) {
                            previousUuid = currentUuid;
                            resume = new Resume(currentUuid, rs.getString("full_name"));
                        }
                        addContact(rs, resume);
                    }
                    if (!previousUuid.isEmpty()) {
                        resumes.add(resume);
                    }
                    return resumes;
                }
        );
    }

    private void saveContacts(Connection cn, Resume resume) throws SQLException {
        Objects.requireNonNull(resume);
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        final String ct = rs.getString("type");
        if (!ct.isEmpty()) {
            r.addContact(
                    ContactType.valueOf(ct),
                    rs.getString("value"));
        }
    }
}
