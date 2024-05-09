package com.es.projectManager.service;

import com.es.projectManager.DAO.CustomUserDao;
import com.es.projectManager.DAO.UserDao;
import com.es.projectManager.model.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public class CustomUserService implements UserService{

    private static CustomUserService customUserService;
    private UserDao userDao;

    private CustomUserService() {
        userDao = CustomUserDao.getInstance();
    }

    public static synchronized CustomUserService getInstance() {
        if (customUserService == null) {
            customUserService = new CustomUserService();
        }
        return customUserService;
    }
    @Override
    public void saveOrUpdate(User entity) {
        userDao.saveOrUpdate(entity);
    }

    @Override
    public List<User> readAll() {
        return userDao.readAll();
    }

    @Override
    public boolean delete(User entity) {
        return userDao.delete(entity);
    }

    @Override
    public Optional<User> read(Integer id) {
        return userDao.read(id);
    }
    @Override
    public Optional<User> readByLogin(String login){
        return userDao.readByLogin(login);
    }

    @Override
    public User getUser(HttpServletRequest request) {
        return userDao.getUser(request);
    }

    @Override
    public void setUser(User user, HttpServletRequest request) {
        userDao.setUser(user, request);
    }

    @Override
    public List<User> readEmployees() {
        return userDao.readEmployees();
    }
}
