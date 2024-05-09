<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="projects" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="employees" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="user" type="com.es.projectManager.model.User" scope="session"/>
<c:set var="servletContextPath" value="${pageContext.servletContext.contextPath}"/>
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
<div class="row justify-content-center">
    <div class="col-md-6 text-center mb-4">
        <h2 class="heading-section">СПИСОК ПРОЕКТОВ</h2>
    </div>
</div>
<div class="container" style="overflow:auto; max-height:700px;">

    <h5 class="text-success">${success}</h5>
    <c:if test="${not empty param.error}">
        <h5 class="text-danger">${param.error}</h5>
    </c:if>
    <div class="form-popup" style="position: page; padding-left: 40px;">
        <a href="#" onmousedown="showPopup('pop_up')" class="img_icon"><img src="${pageContext.servletContext.contextPath}/images/add.png"/></a>
    </div>
    <table class="table table-dark table-bordered ">

        <tr class="col-md-6 text-center mb-4">
            <th>Название</th>
            <th>Начало</th>
            <th>Конец</th>
            <th>Имя заказчика</th>
            <th>Стоимость</th>
            <th>Сотрудники</th>
            <th>Удалить</th>
            <th>Редактировать</th>

        </tr>

        <c:forEach items="${projects}" var="project">
            <c:set var="projectId" value="${project.project_id}"/>
            <tr>
                <td>${project.project_name}</td>
                <td>${project.date_start}</td>
                <td>${project.date_end}</td>
                <td>${user.name}</td>
                <td>${project.cost}</td>
                <td>
                    <div style="overflow:auto; max-height:80px; max-width:150px">
                    <c:forEach items="${project.users}" var="user">
                        <c:if test="${user.type == 'EMPLOYEE'}">
                            ${user.name}<br>
                        </c:if>
                    </c:forEach>
                    </div>
                    <div class="form-popup" >
                        <a href="#" onmousedown="showPopup(${project.project_id})" class="img_icon"><img src="${pageContext.servletContext.contextPath}/images/add.png"/></a>
                        <a href="#" onmousedown="showPopup(${-project.project_id})" class="img_icon"><img src="${pageContext.servletContext.contextPath}/images/remove.png"/></a>
                    </div>
                    <div class = "pop_up" id="${project.project_id}">
                        <div class="pop_up_container">
                            <div class="pop_up_body">
                                <h4 class="mb-4 text-center">
                                    <div class="text-dark font-weight-bold">Добавление сотрудника</div>
                                </h4>

                                <form  class="form-group" id="form2" method="post">

                                    <select class="custom-select-sm" name="select_employee">
                                        <ul>
                                            <c:forEach var="employee" items="${employees}" varStatus="loop">
                                                <c:set var="val" value= "${null}"/>
                                                <c:forEach items="${project.users}" var="user" varStatus="loop">
                                                    <c:if test="${user.user_id == employee.user_id}">
                                                        <c:set var="val" value="1"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${empty val}">
                                                    <option value="${employee.user_id}">${employee.name}</option>
                                                </c:if>
                                            </c:forEach>

                                        </ul>
                                    </select>
                                        <button formaction="${servletContextPath}/customer/${projectId}" type="submit" class="width-30" name="action" value="add_employee">Добавить</button>
                                    <h5 class="text-success">${employeeMessage}</h5>
                                </form>
                                <div class="pop_up_close font-weight-bold text-dark" ONMOUSEDOWN="closePopup()">&#9747</div>
                            </div>
                        </div>
                    </div>
                    <div class = "pop_up" id="${-project.project_id}">
                        <div class="pop_up_container">
                            <div class="pop_up_body">
                                <h4 class="mb-4 text-center">
                                    <div class="text-dark font-weight-bold">Удаление сотрудника</div>
                                </h4>

                                <form  class="form-group" method="post">
                                    <select class="custom-select-sm" name="delete_employee">
                                        <ul>
                                                <c:forEach items="${project.users}" var="user" varStatus="loop">
                                                    <c:if test="${user.type == 'EMPLOYEE'}">
                                                        <option value="${user.user_id}">${user.name}</option>
                                                    </c:if>
                                                </c:forEach>
                                        </ul>
                                    </select>
                                    <button formaction="${servletContextPath}/customer/${projectId}" type="submit" class="width-30" name="action" value="delete_employee">Удалить</button>
                                    <h5 class="text-success">${employeeMessage}</h5>
                                </form>
                                <div class="pop_up_close font-weight-bold text-dark" ONMOUSEDOWN="closePopup()">&#9747</div>
                            </div>
                        </div>
                    </div>
                </td>
                <td>
                    <form action="${servletContextPath}/customer/${projectId}" method="post">
                        <button type="submit" name="action" value="delete_project" class="img_icon"><img src="${pageContext.servletContext.contextPath}/images/delete.png"/></button>
                    </form>
                </td>
                <td>
                    <form action="customer" method="post" >
                        <button type="submit" name="action" value="edit_project" class="img_icon" onmousedown="showPopup(${project.project_id*10})"><img src="${pageContext.servletContext.contextPath}/images/edit.png"/></button>
                    </form>
                    <div class = "pop_up" id="${project.project_id*10}">
                        <div class="pop_up_container">
                            <div class="pop_up_body" >
                                <h5 class="text-danger">${error}</h5>
                                <h5 class="text-success">${success}</h5>
                                <h4 class="mb-4 text-center">
                                    <div class="text-dark font-weight-bold">Редактирование проекта</div>
                                </h4>

                                <form method="post" class="signin-form" >
                                    <div class="form-group">
                                        <input type="text" class="form-popup" placeholder="${project.project_name}" name="project_name" value="${project.project_name}" required>
                                    </div>
                                    <div class="form-group">
                                        <input type="date" class="form-popup" placeholder="${project.date_start}" name="date_beg" value="${project.date_start}" required>
                                    </div>
                                    <div class="form-group">
                                        <input type="date" class="form-popup" placeholder="${project.date_end}" name="date_end" value=${project.date_end} required>
                                    </div>
                                    <div class="form-group">
                                        <input type="number" class="form-popup" placeholder="${project.cost}" name="cost" value="${project.cost}" required>
                                    </div>

                                    <button formaction="${servletContextPath}/customer/${projectId}" type="submit" name="action" class="form-control btn btn-primary submit px-3"  value="add_project">Редактировать проект</button>

                                </form>
                                <div class="pop_up_close font-weight-bold text-dark" onmousedown="closePopup()">&#9747</div>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>

    </form>
    <div class = "pop_up" id="pop_up">
        <div class="pop_up_container">
            <div class="pop_up_body" >
                <h4 class="mb-4 text-center">
                    <div class="text-dark font-weight-bold">Добавление проекта</div>
                </h4>

                <form method="post" class="signin-form" id="form1" >
                    <div class="form-group">
                        <input type="text" class="form-popup" placeholder="Название" name="project_name" required>
                    </div>
                    <div class="form-group">
                        <input type="date" class="form-popup" placeholder="Дата начала" name="date_beg" required>
                    </div>
                    <div class="form-group">
                        <input type="date" class="form-popup" placeholder="Дата завершения" name="date_end" required>
                    </div>
                    <div class="form-group">
                        <input type="number" class="form-popup" placeholder="Стоимость" name="cost" required>
                    </div>
                    <form action="customer" method="post">
                        <button form="form1" type="submit" name="action" class="form-control btn btn-primary submit px-3"  value="add_project">Добавить проект</button>
                    </form>

                </form>
                <div class="pop_up_close font-weight-bold" onmousedown="closePopup()">&#9747</div>
            </div>
        </div>
    </div>

</div>
<script src="scripts/popUp.js"></script>
</body>
</html>