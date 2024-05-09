package com.es.projectManager.DAO;

import com.es.projectManager.model.Project;
import com.es.projectManager.model.User;

import java.util.List;
import java.util.Optional;

public interface ProjectDao extends Dao<Project>{
    List<Project> readByUser(User user);
    Optional<Project> readByName (String name);
}
