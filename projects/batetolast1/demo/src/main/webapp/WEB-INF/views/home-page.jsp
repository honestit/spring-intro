<%--suppress ELValidationInJSP --%>
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

    <title>Home Page</title>
</head>
<body>

    <jsp:include page="main-menu.jsp"/>

    <div class="container">

        <sec:authorize access="isAuthenticated()">
            <div class="row">
                <div class="col-1"></div>
                <div class="col-6"><h2>Dodaj ogłoszenie</h2></div>
                <div class="col-5"></div>
            </div>

            <div class="row">
                <div class="col-2"></div>
                <div class="col-8">
                    <form method="post" action="${pageContext.request.contextPath}/add-advert">
                        <div class="form-group">
                            <label for="title">Tytuł</label>
                            <input type="text" required name="title" id="title" class="form-control"
                                   placeholder="Podaj tytuł ogłoszenia"/>
                        </div>
                        <div class="form-group">
                            <label for="description">Opis</label>
                            <textarea required name="description" id="description" class="form-control"
                                      placeholder="Podaj opis"></textarea>
                        </div>
                        <div>
                            <label for="categories">Kategoria:</label>
                            <select id="categories" name="categoryId">
                                <c:forEach var="categoryDTO" items="${categoryDTOs}">
                                    <option value="<c:out value="${categoryDTO.id}"/>"><c:out
                                            value="${categoryDTO.name}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                        <button class="btn btn-primary" type="submit">Zarejestruj</button>
                        <button class="btn btn-secondary" type="reset">Wyczyść dane</button>
                        <sec:csrfInput/>
                    </form>
                </div>
                <div class="col-2"></div>
            </div>
        </sec:authorize>

        <div class="row">
            <div class="col-1"></div>
            <div class="col-6"><h2>Lista ogłoszeń</h2></div>
            <div class="col-5"></div>
        </div>

        <div class="row">
            <div class="col-12">
                <table class="table border-bottom">
                    <thead>
                    <tr class="d-flex">
                        <th class="col-1">ID</th>
                        <th class="col-2">TYTUŁ</th>
                        <th class="col-5">OPIS</th>
                        <th class="col-2">KATEGORIA</th>
                        <th class="col-2">UŻYTKOWNIK</th>
                        <th class="col-2">DODANO</th>
                        <sec:authorize access="isAuthenticated()">
                            <th class="col-2">AKCJE</th>
                        </sec:authorize>
                    </tr>
                    </thead>
                    <tbody class="text-color-lighter">
                    <c:forEach var="advertDTO" items="${advertDTOs}" varStatus="counter">
                        <tr class="d-flex">
                            <td class="col-1">${counter.index + 1}</td>
                            <td class="col-2"><c:out value="${advertDTO.title}"/></td>
                            <td class="col-5"><c:out value="${advertDTO.description}"/></td>
                            <td class="col-2"><c:out value="${advertDTO.categoryName}"/></td>
                            <td class="col-2">
                                <sec:authorize access="isAuthenticated()">
                                    <a href="/user-adverts/<c:out value="${advertDTO.ownerId}"/>"><c:out
                                            value="${advertDTO.ownerUsername}"/></a>
                                </sec:authorize>
                                <sec:authorize access="!isAuthenticated()">
                                    <c:out value="${advertDTO.ownerUsername}"/>
                                </sec:authorize>
                            </td>
                            <td class="col-2"><c:out value="${advertDTO.posted}"/></td>

                            <td class="col-2">
                                <sec:authorize access="isAuthenticated()">
                                    <c:if test="${!advertDTO.createdByLoggedUser}">
                                        <c:if test="${advertDTO.observed}">
                                            <form class="form-inline mt-3" method="post"
                                                  action="${pageContext.request.contextPath}/unobserve-advert">
                                                <input type="hidden" name="advertId" value="${advertDTO.id}">
                                                <button class="btn btn-outline-primary" type="submit">Przestań
                                                    obserwować
                                                </button>
                                                <sec:csrfInput/>
                                            </form>
                                        </c:if>
                                        <c:if test="${!advertDTO.observed}">
                                            <form class="form-inline mt-3" method="post"
                                                  action="${pageContext.request.contextPath}/observe-advert">
                                                <input type="hidden" name="advertId" value="${advertDTO.id}">
                                                <button class="btn btn-outline-primary" type="submit">Obserwuj
                                                    ogłoszenie
                                                </button>
                                                <sec:csrfInput/>
                                            </form>
                                        </c:if>
                                    </c:if>
                                </sec:authorize>
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
