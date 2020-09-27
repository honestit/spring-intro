<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<nav class="navbar navbar-expand-lg navbar-light">
    <a class="navbar-bran mr-2" href="https://github.com/batetolast1">batetolast1</a>
    <div class="collapse navbar-collapse">
        <ul class="navbar-nav nav nav-pills nav-fill">
            <li class="nav-item active mx-1">
                <a class="nav-link btn btn-outline-info" role="button" href="/">
                    <span class="text-lg-left">Strona główna</span></a>
            </li>
            <sec:authorize access="isAuthenticated()">
                <li class="nav-item active mx-1">
                    <a class="nav-link btn btn-outline-info" role="button" href="/user-adverts">
                        <span class="text-lg-left">Twoje ogłoszenia</span></a>
                </li>
                <li class="nav-item active mx-1">
                    <a class="nav-link btn btn-outline-info" role="button" href="/observed-adverts">
                        <span class="text-lg-left">Obserwowane ogłoszenia</span></a>
                </li>
            </sec:authorize>
        </ul>
    </div>

    <%-- Sekcja dostępna tylko dla niezalogowanych użytkowników --%>

    <sec:authorize access="!isAuthenticated()">
        <div style="margin-right: 20px">
            Witaj, <strong>nieznajomy</strong>
        </div>
        <a class="btn btn-outline-success" style="margin-right: 20px" role="button" href="/register">
            <span class="text-lg-left">Zarejestruj</span></a>
        <a class="btn btn-outline-primary" role="button" href="/login">
            <span class="text-lg-left">Zaloguj</span></a>
    </sec:authorize>

    <%-- Sekcja dostępna tylko dla zalogowanych użytkowników --%>

    <sec:authorize access="isAuthenticated()">
        <div style="margin-right: 20px">
            Witaj, <strong>${pageContext.request.userPrincipal.principal.username}</strong>
        </div>
        <form class="form-inline mt-3" method="post" action="/logout">
            <button class="btn btn-outline-primary" type="submit">Wyloguj</button>
            <sec:csrfInput/>
        </form>
    </sec:authorize>

</nav>
