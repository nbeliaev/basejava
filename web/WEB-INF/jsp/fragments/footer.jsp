<%@ page import="ru.javawebinar.basejava.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<nav class="navbar navbar-light bg-light">
    <footer>total: <%= ((List<Resume>) request.getAttribute("resumes")).size() %>
    </footer>
</nav>