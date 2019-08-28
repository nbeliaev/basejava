package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private final Storage storage = new SqlStorage(
            Config.getInstance().getDbUrl(),
            Config.getInstance().getDbUser(),
            Config.getInstance().getDbPwd());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final String uuid = req.getParameter("uuid");
        final String action = req.getParameter("action");
        if (uuid == null && action == null) {
            req.setAttribute("resumes", storage.getAllSorted());
            req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);
        } else {
            final Resume r;
            switch (action) {
                case "delete":
                    storage.delete(uuid);
                    resp.sendRedirect("resume");
                    break;
                case "edit":
                    r = storage.get(uuid);
                    req.setAttribute("resume", r);
                    req.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(req, resp);
                    break;
                case "create":
                    r = new Resume();
                    req.setAttribute("resume", r);
                    req.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(req, resp);
                    break;
                case "view":
                    r = storage.get(uuid);
                    req.setAttribute("resume", r);
                    req.getRequestDispatcher("/WEB-INF/jsp/view.jsp").forward(req, resp);
                    break;
                default:
                    throw new IllegalArgumentException(action + " is not supported.");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        final String fullName = req.getParameter("fullName");
        final String uuid = req.getParameter("uuid");
        final Resume resume;
        if (uuid.isEmpty()) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            final String value = req.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                resume.addContact(type, value);
            } else {
                resume.removeContact(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            final String value = req.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                Section section;
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        section = new SimpleSection(value);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        section = new ListSection(List.of(value.split("\n")));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown section type");
                }
                resume.addSection(type, section);
            } else {
                resume.removeSection(type);
            }
        }
        if (uuid.isEmpty()) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        resp.sendRedirect("resume");
    }
}
