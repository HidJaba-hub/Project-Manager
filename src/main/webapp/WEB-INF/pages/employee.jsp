<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="projects" type="java.util.ArrayList" scope="request"/>
<html>
<tags:head></tags:head>
<body>
<nav role="navigation" class="navbar navbar-default">

    <nav role="navigation" class="navbar navbar-default">

        <div class="navbar-collapse">
            <ul class="nav navbar-nav">
                <li><p class="w-100 h4"><a href="login">&#10229 Вернуться</a></p></li>
            </ul>
        </div>

    </nav>
</nav>

    <div class="col-md-6 text-left">
        <h2 class="heading-section text-darkgreen">Здравствуйте, ${user.name}</h2>
    </div>

<div class="container">
    <table border="4" class="table table-dark table-bordered ">
        <div class="row justify-content-center">
            <div class="col-md-6 text-center mb-4">
                <h2 class="heading-section">СПИСОК ПРОЕКТОВ</h2>
            </div>
        </div>
        <tr class="col-md-6 text-center mb-4">
            <th>Название</th>
            <th>Начало</th>
            <th>Конец</th>
            <th>Имя заказчика</th>
            <th>Стоимость</th>
            <th>Сотрудники</th>
        </tr>
        <c:forEach items="${projects}" var="project">
            <c:set var="projectId" value="${project.project_id}"/>
            <tr>
            <td>${project.project_name}</td>
            <td>${project.date_start}</td>
            <td>${project.date_end}</td>
            <td>

                <c:forEach items="${project.users}" var="user">
                <c:if test="${user.type == 'CUSTOMER'}">
                    ${user.name}
                </c:if>
            </c:forEach>

            </td>
            <td>${project.cost}</td>
            <td>
                <div style="overflow:auto; max-height:80px; max-width:150px">
            <c:forEach items="${project.users}" var="user">
                <c:if test="${user.type == 'EMPLOYEE'}">
                    ${user.name}<br>
                </c:if>
            </c:forEach>
                </div>
        </c:forEach>
    </table>
</div>
<script src="scripts/popUp.js"></script>
</body>
</html>
