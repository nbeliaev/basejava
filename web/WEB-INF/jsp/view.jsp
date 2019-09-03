<%@ page import="ru.javawebinar.basejava.util.HtmlUtil" %>
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
    <h2>${resume.fullName}&ensp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.png" alt="edit"></a>
    </h2>
    <br>
    <section class="">
        <h4>Contacts</h4>
        <table class=table>
            <c:forEach var="contactEntry" items="${resume.contacts}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <tr>
                    <td>${contactEntry.key.title}</td>
                    <td><%=HtmlUtil.contactPreview(contactEntry)%>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </section>
    <hr>
    <c:if test="${resume.sections.size() != 0}">
        <section>
            <h4>Characteristics</h4>
            <table class=table>
                <c:forEach var="sectionEntry" items="${resume.sections}">
                    <jsp:useBean id="sectionEntry"
                                 type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.Section>"/>
                    <tr>
                        <td>${sectionEntry.key.title}</td>
                        <td><%=HtmlUtil.sectionPreview(sectionEntry)%>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </section>
    </c:if>
</section>
</body>
</html>
