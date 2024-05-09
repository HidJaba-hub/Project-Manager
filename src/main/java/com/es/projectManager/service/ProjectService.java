package com.es.projectManager.service;

import com.es.projectManager.model.Project;
import com.es.projectManager.model.User;

import java.util.List;
import java.util.Optional;

public interface ProjectService extends Service<Project> {
    List<Project> readByUser(User user);
    Optional<Project> readByName(String name);
}
