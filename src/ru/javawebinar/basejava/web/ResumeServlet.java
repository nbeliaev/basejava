package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;
import ru.javawebinar.basejava.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
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
                    return;
                case "edit":
                case "view":
                    r = storage.get(uuid);
                    break;
                case "create":
                    r = new Resume();
                    break;
                default:
                    throw new IllegalArgumentException(action + " is not supported.");
            }
            req.setAttribute("resume", r);
            req.getRequestDispatcher("/WEB-INF/jsp/" + (action.equals("view") ? "view.jsp" : "edit.jsp")).forward(req, resp);
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
            final String[] values = req.getParameterValues(type.name());
            if (StringUtil.isBlank(value) && values == null) {
                resume.removeSection(type);
            } else {
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
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = new ArrayList<>();
                        for (int i = 0; i < values.length; i++) {
                            if (StringUtil.isBlank(values[i])) {
                                continue;
                            }
                            final String prfx = type.name() + i;
                            final String link = req.getParameter(prfx + "link");
                            List<Organization.Position> positions = new ArrayList<>();
                            final String[] startDates = req.getParameterValues(prfx + "start");
                            final String[] endDates = req.getParameterValues(prfx + "end");
                            final String[] titles = req.getParameterValues(prfx + "title");
                            final String[] descriptions = req.getParameterValues(prfx + "description");
                            for (int j = 0; j < startDates.length; j++) {
                                Organization.Position position = new Organization.Position(
                                        startDates[j].isEmpty() ? DateUtil.NOW : LocalDate.parse(startDates[j]),
                                        endDates[j].isEmpty() ? DateUtil.NOW : LocalDate.parse(endDates[j]),
                                        titles[j],
                                        descriptions[j]);
                                positions.add(position);
                            }
                            Organization organization = new Organization(values[i], link, positions);
                            organizations.add(organization);
                        }
                        section = new OrganizationSection(organizations);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown section type");
                }
                resume.addSection(type, section);
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
