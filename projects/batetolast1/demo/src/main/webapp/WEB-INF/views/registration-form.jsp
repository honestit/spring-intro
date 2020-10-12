<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">

    <title>Registration</title>
</head>
<body>

    <jsp:include page="main-menu.jsp"/>

    <div class="container">
        <div class="row">
            <div class="col-1"></div>
            <div class="col-10"><h2>Rejestracja</h2></div>
            <div class="col-1"></div>
        </div>

        <div class="row">
            <div class="col-1"></div>
            <div class="col-6">
                <form:form method="post" modelAttribute="registrationData">
                    <form method="post" action="${pageContext.request.contextPath}/register">
                        <div class="form-group">
                            <form:label path="username">Nazwa użytkownika</form:label>
                            <form:input path="username" cssClass="form-control" required="true"
                                        placeholder="Podaj nazwę użytkownika"/>
                            <form:errors path="username" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="firstName">Imię</form:label>
                            <form:input path="firstName" cssClass="form-control" required="true"
                                        placeholder="Podaj imię"/>
                        </div>
                        <div class="form-group">
                            <form:label path="lastName">Nazwisko</form:label>
                            <form:input path="lastName" cssClass="form-control" required="true"
                                        placeholder="Podaj nazwisko"/>
                        </div>
                        <div class="form-group">
                            <form:label path="password">Hasło</form:label>
                            <form:password path="password" cssClass="form-control" required="true"
                                           placeholder="Podaj hasło"/>
                            <form:errors path="password" element="p"/>
                        </div>
                        <button class="btn btn-primary" type="submit">Zarejestruj</button>
                        <button class="btn btn-secondary" type="reset">Wyczyść dane</button>
                        <sec:csrfInput/>
                    </form>
                </form:form>
            </div>
            <div class="col-5"></div>
        </div>
    </div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
            integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
            integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
            crossorigin="anonymous"></script>
</body>
</html>