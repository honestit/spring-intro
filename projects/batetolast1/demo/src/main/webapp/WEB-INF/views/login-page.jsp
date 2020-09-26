<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">

    <title>Login</title>
</head>
<body>

    <jsp:include page="main-menu.jsp"/>

    <div class="container">
        <div class="row">
            <div class="col-1"></div>
            <div class="col-10"><h2>Logowanie</h2></div>
            <div class="col-1"></div>
        </div>
        <div class="row">
            <div class="col-1"></div>
            <div class="col-6">
                <c:if test="${param['error'] != null}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        Błędne dane logowania!
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>
                <form method="post" action="/login">
                    <div class="form-group">
                        <label for="username">Nazwa użytkownika</label>
                        <input type="text" required name="username" id="username" class="form-control"
                               placeholder="Podaj nazwę użytkownika"/>
                    </div>
                    <div class="form-group">
                        <label for="password">Hasło</label>
                        <input type="password" required name="password" id="password" class="form-control"
                               placeholder="Podaj hasło"/>
                    </div>
                    <button class="btn btn-primary" type="submit">Zaloguj</button>
                    <button class="btn btn-secondary" type="reset">Wyczyść dane</button>
                    <sec:csrfInput/>
                </form>
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
