package com.es.projectManager.DAO;

import com.es.projectManager.model.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<User>{
    Optional<User> readByLogin(String login);
    User getUser(HttpServletRequest request);
    void setUser(User user,  HttpServletRequest request);

    List<User> readEmployees();
}
