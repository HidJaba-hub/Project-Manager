package com.es.projectManager.service;

import com.es.projectManager.model.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public interface UserService extends Service<User>{
    default Optional<User> readByLogin(String login){return Optional.empty();}
    User getUser(HttpServletRequest request);
    void setUser( User user, HttpServletRequest request);
    List<User> readEmployees();
}
