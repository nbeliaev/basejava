<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-light bg-light">
    <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
    <footer>total: ${resumes.size()}
    </footer>
</nav>