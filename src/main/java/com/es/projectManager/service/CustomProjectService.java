package com.es.projectManager.service;

import com.es.projectManager.DAO.CustomProjectDao;
import com.es.projectManager.DAO.ProjectDao;
import com.es.projectManager.model.Project;
import com.es.projectManager.model.User;

import java.util.List;
import java.util.Optional;

public class CustomProjectService implements ProjectService{

    private static CustomProjectService customProjectService;
    private ProjectDao projectDao;

    private CustomProjectService() {
        projectDao = CustomProjectDao.getInstance();
    }

    public static synchronized CustomProjectService getInstance() {
        if (customProjectService == null) {
            customProjectService = new CustomProjectService();
        }
        return customProjectService;
    }
    @Override
    public void saveOrUpdate(Project project) {
        projectDao.saveOrUpdate(project);
    }

    @Override
    public List<Project> readAll() {
        return projectDao.readAll();
    }

    @Override
    public boolean delete(Project entity) {
        return projectDao.delete(entity);
    }

    @Override
    public Optional<Project> read(Integer id) {
        return projectDao.read(id);
    }

    @Override
    public List<Project> readByUser(User user) {
        return projectDao.readByUser(user);
    }

    @Override
    public Optional<Project> readByName(String name) {
        return projectDao.readByName(name);
    }
}
