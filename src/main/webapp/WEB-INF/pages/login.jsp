<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<html>
<tags:head></tags:head>
<body class="img js-fullheight" >
<section class="ftco-section">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-5 text-center mb-5">
                <h1 class="heading-section">Вход в систему</h1>
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col-md-6 col-lg-4">
                <div class="login-wrap p-0">
                    <h5 class="btn-danger text-center ">${errorMessage}</h5>
                    <h4 class="mb-4 text-center">
                        <div class="text-darkgreen">Есть аккаунт?</div>

                    </h4>

                    <form action="login" method="POST" class="signin-form">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Логин" name="login" required>
                        </div>
                        <div class="form-group">
                            <input id="password-field" type="password" class="form-control" placeholder="Пароль" name="password" required>
                        </div>
                        <div class="form-group">
                            <input type="submit" class="form-control btn btn-primary submit px-3" value="Войти"/>
                        </div>

                    </form>
                    <h5 class="mb-2 text-center"><a href="registration">&mdash; Регистрация &mdash;</a></h5>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
