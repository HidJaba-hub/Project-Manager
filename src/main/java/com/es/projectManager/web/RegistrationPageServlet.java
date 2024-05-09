package com.es.projectManager.web;

import com.es.projectManager.model.User;
import com.es.projectManager.model.UserType;
import com.es.projectManager.service.CustomUserService;
import com.es.projectManager.service.UserService;
import com.es.projectManager.util.HashPassword;
import com.es.projectManager.validation.Validator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RegistrationPageServlet extends HttpServlet {
    public UserService userService;
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        userService = CustomUserService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!getParameters(request,response)) {
            request.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(request, response);
        }
    }
    private boolean getParameters(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        boolean isCorrect = true;
        String name = request.getParameter("name");
        if (!Validator.correctBigLetter(name)){
            request.setAttribute("errorName", "Неверное имя");
            isCorrect = false;
        }
        String login=request.getParameter("login");
        if(userService.readByLogin(login).isPresent()){
            request.setAttribute("errorLogin", "Такой логин уже существует");
            isCorrect = false;
        }
        String password = request.getParameter("password");
        UserType userType = UserType.valueOf(request.getParameter("userType"));
        if (!isCorrect) return false;
        User user = SetUser(name, login, password, userType);
        userService.setUser(user, request);
        switch (user.getType()) {
            case CUSTOMER ->  response.sendRedirect(request.getContextPath() + "/customer");
            case EMPLOYEE -> response.sendRedirect(request.getContextPath() + "/employee");
        }
        return true;
    }
    private User SetUser(String name, String login, String password, UserType userType){
        User user = new User(name, login, HashPassword.getHash(password), userType);
        userService.saveOrUpdate(user);
        return user;
    }
}
