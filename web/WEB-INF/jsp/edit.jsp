<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.SimpleSection" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <jsp:useBean id="resume" scope="request" class="ru.javawebinar.basejava.model.Resume"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Name:</dt>
            <dd><input type="text" name="fullName" class="form-control" required value="${resume.fullName}">
            </dd>
        </dl>
        <br>
        <h4>Contacts</h4>
        <c:forEach var="contact" items="<%=ContactType.values()%>">
            <jsp:useBean id="contact" type="ru.javawebinar.basejava.model.ContactType"/>
            <dl>
                <dt>${contact.title}</dt>
                <dd><input type="text" name="${contact.name()}" class="form-control"
                           value="${resume.getContact(contact)}">
                </dd>
            </dl>
        </c:forEach>
        <h4>Characteristics</h4>
        <c:forEach var="section" items="<%=SectionType.values()%>">
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.SectionType"/>
            <dl>
                <c:choose>
                    <c:when test="${section==SectionType.PERSONAL || section==SectionType.OBJECTIVE}">
                        <dt>${section.title}</dt>
                        <dd><input type="text" name="${section.name()}" class="form-control"
                                   value="<%=resume.getSection(section)!=null?((SimpleSection)resume.getSection(section)).getContent() : ""%>">
                        </dd>
                    </c:when>
                    <c:when test="${section==SectionType.ACHIEVEMENT || section==SectionType.QUALIFICATIONS}">
                        <dt>${section.title}</dt>
                        <dd><textarea name="${section.name()}"
                                      class="form-control"><%=resume.getSection(section) != null ? String.join("\n", ((ListSection) resume.getSection(section)).getContent()) : ""%></textarea>
                        </dd>
                    </c:when>
                </c:choose>
            </dl>
        </c:forEach>
        <button type = "reset" onclick="location.href='resume'" class="btn btn-light">Cancel</button>
        <button type="submit" class="btn btn-success">Save</button>
    </form>
</section>
</body>
</html>
