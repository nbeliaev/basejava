package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SqlStorage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private final SqlStorage storage = new SqlStorage(
            Config.getInstance().getDbUrl(),
            Config.getInstance().getDbUser(),
            Config.getInstance().getDbPwd());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("text/html charset=utf-8");
        response.getWriter().write(initPage(request.getParameter("uuid")));
    }

    private String initPage(String uuid) {
        StringBuilder htmlString = new StringBuilder();
        htmlString.append("<html'><head><meta charset='utf-8'>");
        htmlString.append("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">");
        htmlString.append("</head><body><table class = table>");
        htmlString.append("<thead><tr><th scope='col'>#</th><th scope='col'>uuid</th><th scope='col'>name</th></tr></thead>");
        final List<Resume> resumes = getResumes(uuid);
        for (int i = 0; i < resumes.size(); i++) {
            htmlString.append("<tr>");
            htmlString.append("<th scope = 'row'>").append((i + 1)).append("</th>");
            htmlString.append("<td>").append(resumes.get(i).getUuid()).append("</td>");
            htmlString.append("<td>").append(resumes.get(i).getFullName()).append("</td>");
            htmlString.append("</tr>");
        }
        htmlString.append("</table>").append("</body>").append("</html>");
        return htmlString.toString();
    }

    private List<Resume> getResumes(String uuid) {
        if (uuid == null) {
            return storage.getAllSorted();
        } else {
            final List<Resume> resumes = new ArrayList<>(1);
            resumes.add(storage.get(uuid));
            return resumes;
        }
    }
}
