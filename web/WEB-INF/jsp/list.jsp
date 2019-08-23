<%@ page import="ru.javawebinar.basejava.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Resumes list</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table class=table>
        <thead>
        <tr>
            <th scope='col'>#</th>
            <th scope='col'>name</th>
            <th scope='col'>e-mail</th>
            <th scope='col'>edit</th>
            <th scope='col'>delete</th>
        </tr>
        </thead>
        <%
            final List<Resume> resumes = (List<Resume>) request.getAttribute("resumes");
            for (int i = 0; i < resumes.size(); i++) {
        %>
        <tr>
            <th scope='row'><%=i%>
            </th>
            <td>
                <a href="resume?=<%=resumes.get(i).getUuid()%>"><%=resumes.get(i).getFullName()%>
                </a>
            </td>
            <td>
                <%=resumes.get(i).getContact(ContactType.EMAIL)%>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>