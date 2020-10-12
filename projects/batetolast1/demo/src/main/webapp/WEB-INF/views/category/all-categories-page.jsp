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

    <title>Dodaj kategorię</title>
</head>
<body>

    <jsp:include page="../main-menu.jsp"/>

    <div class="container">
        <div class="row">
            <div class="col-1"></div>
            <div class="col-6"><h2>Dodaj kategorię</h2></div>
            <div class="col-5"></div>
        </div>

        <div class="row">
            <div class="col-2"></div>
            <div class="col-8">
                <form method="post" action="${pageContext.request.contextPath}/category/add">
                    <div class="form-group">
                        <label for="name">Nazwa</label>
                        <input type="text" required name="name" id="name" class="form-control"
                               placeholder="Podaj nazwę kategorii"/>
                    </div>
                    <button class="btn btn-primary" type="submit">Dodaj</button>
                    <button class="btn btn-secondary" type="reset">Wyczyść dane</button>
                    <sec:csrfInput/>
                </form>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row">
            <div class="col-1"></div>
            <div class="col-6"><h2>Lista kategorii</h2></div>
            <div class="col-5"></div>
        </div>

        <div class="row">
            <div class="col-12">
                <table class="table border-bottom">
                    <thead>
                    <tr class="d-flex">
                        <th class="col-1">ID</th>
                        <th class="col-5">NAZWA</th>
                        <th class="col-2">LICZBA OGŁOSZEŃ</th>
                        <th class="col-2">AKCJE</th>
                    </tr>
                    </thead>
                    <tbody class="text-color-lighter">
                    <c:forEach var="categoryDTO" items="${showCategoryDTOs}" varStatus="counter">
                        <tr class="d-flex">
                            <td class="col-1">${counter.index + 1}</td>
                            <td class="col-5"><c:out value="${categoryDTO.name}"/></td>
                            <td class="col-2"><c:out value="${categoryDTO.advertsCount}"/></td>
                            <td class="col-2">
                                <c:if test="${categoryDTO.allowToDelete}">
                                    <form class="form-inline mt-3" method="post"
                                          action="${pageContext.request.contextPath}/category/delete">
                                        <input type="hidden" name="id" value="${categoryDTO.id}">
                                        <button class="btn btn-outline-primary" type="submit">Usuń</button>
                                        <sec:csrfInput/>
                                    </form>
                                </c:if>

                                <form class="form-inline mt-3" method="get"
                                      action="${pageContext.request.contextPath}/category/edit">
                                    <input type="hidden" name="id" value="${categoryDTO.id}">
                                    <button class="btn btn-outline-primary" type="submit">Edytuj</button>
                                    <sec:csrfInput/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
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
