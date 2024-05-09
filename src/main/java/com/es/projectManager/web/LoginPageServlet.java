package com.es.projectManager.web;

import com.es.projectManager.model.User;
import com.es.projectManager.service.CustomUserService;
import com.es.projectManager.service.UserService;
import com.es.projectManager.util.HashPassword;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class LoginPageServlet extends HttpServlet {
    public UserService userService;
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        userService = CustomUserService.getInstance();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("errorMessage", " ");
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login, password;
        login = request.getParameter("login");
        password = request.getParameter("password");
        request.setAttribute("errorMessage", " ");
        Optional<User> user = userService.readByLogin(login);
        if(user.isEmpty()){
            request.setAttribute("errorMessage", "Такого пользователя не существует");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }
        if(Arrays.equals(user.get().getPassword(), HashPassword.getHash(password))){
            userService.setUser(user.get(), request);
            request.setAttribute("errorMessage", " ");
            switch (user.get().getType()) {
                case CUSTOMER ->  response.sendRedirect(request.getContextPath() + "/customer");
                case EMPLOYEE ->  response.sendRedirect(request.getContextPath() + "/employee");
            }

        }
        else{
            request.setAttribute("errorMessage", "Проверьте корректность пароля");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        }
    }
}
