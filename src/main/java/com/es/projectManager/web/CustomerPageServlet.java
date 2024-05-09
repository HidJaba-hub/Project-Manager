package com.es.projectManager.web;

import com.es.projectManager.model.Project;
import com.es.projectManager.model.User;
import com.es.projectManager.service.*;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CustomerPageServlet extends HttpServlet {
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
        request.setAttribute("employees", userService.readEmployees());
        request.getRequestDispatcher("/WEB-INF/pages/customer.jsp").forward(request, response);
    }
    private List<Project> getProjectList(User user){
        List<Project> projects = new ArrayList<>();
        if(user.getProjects()==null) return projects;
        projects.addAll((List) user.getProjects());
        return projects;
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String errors = "";
        //request.setAttribute("employees", userService.readEmployees());
        if(action!=null){
            switch (action){
                case "add_project" -> errors=setProject(request, response, errors);
                case "add_employee" -> addEmployee(request);
                case "delete_project" -> deleteProject(request);
                case "delete_employee" -> deleteEmployee(request);
            }
        }
        if(errors.isEmpty()) {
            request.setAttribute("employees", userService.readEmployees());
            response.sendRedirect(request.getContextPath() + "/customer");
        }
        else {
            request.setAttribute("projects", getProjectList(userService.getUser(request)));
            request.setAttribute("employees", userService.readEmployees());
            response.sendRedirect(request.getContextPath() + "/customer?error="+errors);
        }
        //request.getRequestDispatcher("/WEB-INF/pages/customer.jsp").forward(request, response);
    }
    private void deleteEmployee(HttpServletRequest request){
        if(request.getPathInfo() == null) return;
        User user = userService.getUser(request);
        int employee_id = Integer.parseInt(request.getParameter("delete_employee"));
        User employee = userService.read(employee_id).get();

        int projectId = Integer.valueOf(request.getPathInfo().substring(1));

        Project project = projectService.read(projectId).get();
        List<User> users = new ArrayList<>();
        for (User emp: project.getUsers()) {
            if(Objects.equals(emp.getUser_id(), employee.getUser_id()))continue;
            users.add(emp);
        }
        project.setUsers(users);
        projectService.saveOrUpdate(project);
        userService.setUser(userService.read(user.getUser_id().intValue()).get(),request);
        request.setAttribute("projects", getProjectList(userService.getUser(request)));
    }
    private void deleteProject(HttpServletRequest request){
        User user = userService.getUser(request);
        int projectId = Integer.valueOf(request.getPathInfo().substring(1));
        projectService.delete(projectService.read(projectId).get());
        userService.setUser(userService.read(user.getUser_id().intValue()).get(),request);
        request.setAttribute("projects", getProjectList(userService.getUser(request)));
    }
    private void addEmployee(HttpServletRequest request){
        if(request.getPathInfo() == null) return;
        User user = userService.getUser(request);
        int employee_id = Integer.parseInt(request.getParameter("select_employee"));
        User employee = userService.read(employee_id).get();
        int projectId = Integer.valueOf(request.getPathInfo().substring(1));

        Project project = projectService.read(projectId).get();
        List<User> users = new ArrayList<>();
        users = project.getUsers();
        users.add(employee);
        project.setUsers(users);
        projectService.saveOrUpdate(project);
        userService.setUser(userService.read(user.getUser_id().intValue()).get(),request);
        request.setAttribute("projects", getProjectList(userService.getUser(request)));
        //List<String> employees = Arrays.stream(request.getParameterValues("employees")).toList();
    }
    private String setProject(HttpServletRequest request, HttpServletResponse response, String error) throws ServletException, IOException{
        Project project = new Project();

        if(request.getPathInfo()!=null){
            int project_id = Integer.parseInt(request.getPathInfo().substring(1));
            project = projectService.read(project_id).get();
        }
        String project_name = request.getParameter("project_name");
        if (project.getProject_id()==null && projectService.readByName(project_name).isPresent()) {
            error+="This project exists";
        }
        if(project.getProject_name() !=null && !project_name.equals(project.getProject_name()) && projectService.readByName(project_name).isPresent()){
            error+="This project exists";
        }
        String date_beg =request.getParameter("date_beg");
        String date_end =request.getParameter("date_end");
        error = SetDate(project, date_beg, date_end, error);
        if(!error.isEmpty()){
            request.setAttribute("error", "Project has not been added: "+error);
            return error;
        }
        project.setProject_name(project_name);
        project.setCost(BigInteger.valueOf(Integer.parseInt(request.getParameter("cost"))));
        if(error.isEmpty()) {
            User user = userService.getUser(request);
            if(project.getProject_id()==null) {
                List<User> users = new ArrayList<>();
                if (project.getUsers() == null) {
                    users.add(user);
                } else {
                    users = project.getUsers();
                    users.add(user);
                }
                project.setUsers(users);
            }
            projectService.saveOrUpdate(project);
            userService.setUser(userService.read(user.getUser_id().intValue()).get(),request);
            request.setAttribute("projects", getProjectList(userService.getUser(request)));
        }
        return error;
    }

    private String SetDate (Project project, String date_start, String date_end, String error){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(date_start, formatter);
        LocalDate endDate = LocalDate.parse(date_end, formatter);
        if (startDate.isAfter(LocalDate.now())) {
            if(error.isEmpty()) project.setDate_end(endDate);
        } else {
            error += " Date cant be past";
        }
        if(startDate.isAfter(endDate)){
            error += " Date must be earlier than start";
        }
        project.setDate_start(startDate);
        return error;
    }
}
