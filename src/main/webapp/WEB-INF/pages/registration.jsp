<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<%--<jsp:useBean id="user" type="com.es.projectManager.model.User" scope="session"/>--%>
<html>
<tags:head></tags:head>
<body class="img js-fullheight" >
<section class="ftco-section">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-5 text-center mb-5">
                <h1 class="heading-section">Регистрация</h1>
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col-md-6 col-lg-4">
                <div class="login-wrap p-0">
                    <h5 class="btn-danger text-center ">${errorMessage}</h5>
                    <h5 class="btn-success text-center ">${successMessage}</h5>

                    <form action="registration" method="POST" class="signin-form">
                        <div class="form-group">
                            <p class="text-danger text-left mb-auto">${errorName}</p>
                            <input type="text" class="form-control " placeholder="Введите ваше имя" name="name" required>
                        </div>
                        <div class="form-group">
                            <p class="text-danger text-left mb-auto">${errorLogin}</p>
                            <input type="text" class="form-control" placeholder="Придумайте логин" name="login" required>
                        </div>
                        <div class="form-group">
                            <input id="password-field" type="password" class="form-control" placeholder="Придумайте пароль" name="password" required>
                        </div>
                        <div class="form-group">
                            <select class="custom-select" name="userType">
                                <ul>
                                <option value="CUSTOMER">Заказчик</option>
                                <option value="EMPLOYEE">Сотрудник</option>
                                </ul>
                            </select>
                        </div>
                        <div class="form-group">
                            <input type="submit" class="form-control btn btn-primary submit px-3" value="Зарегистрировать"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>