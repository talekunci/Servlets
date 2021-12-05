package ua.goit.servlets.projectServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.model.Project;
import ua.goit.service.HandleBodyUtil;
import ua.goit.service.ProjectService;

import java.io.IOException;
import java.util.List;

@WebServlet("/projects")
public class ProjectsServlet extends HttpServlet {

    private ProjectService service;

    @Override
    public void init() throws ServletException {
        this.service = (ProjectService) getServletContext().getAttribute("projectService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deleteId = req.getParameter("deleteId");
        if (deleteId != null) {
            service.delete(Long.parseLong(deleteId));
            resp.sendRedirect("/projects");
        } else {
            List<Project> all = service.getAll();
            req.setAttribute("projects", all);
            req.getRequestDispatcher("/jsp/projects.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HandleBodyUtil.getModelFromStream(req.getInputStream(), Project.class)
                .ifPresent(project -> service.create(project));
        resp.sendRedirect("/projects");
    }

}
