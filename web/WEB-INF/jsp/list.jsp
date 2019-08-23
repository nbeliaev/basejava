<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
        <c:forEach var="resume" items="${resumes}" varStatus="loop">
            <jsp:useBean id="resume" class="ru.javawebinar.basejava.model.Resume"/>
            <tr>
                <th scope='row'>${loop.index} </th>
                <td><a href="resume?=${resume.uuid}">${resume.fullName} </a></td>
                <td> ${resume.getContact(ContactType.EMAIL)} </td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>