package com.es.projectManager.web;

import com.es.projectManager.model.Project;
import com.es.projectManager.model.User;
import com.es.projectManager.service.CustomProjectService;
import com.es.projectManager.service.CustomUserService;
import com.es.projectManager.service.ProjectService;
import com.es.projectManager.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeePageServlet extends HttpServlet {

    public ProjectService projectService;
    public UserService userService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        projectService = CustomProjectService.getInstance();
        userService = CustomUserService.getInstance();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = userService.getUser(request);

        request.setAttribute("projects", getProjectList(user));
        request.getRequestDispatcher("/WEB-INF/pages/employee.jsp").forward(request, response);
    }
    private List<Project> getProjectList(User user){
        List<Project> projects = new ArrayList<>();
        if(user.getProjects()==null) return projects;
        projects.addAll((List) user.getProjects());
        return projects;
    }
}
