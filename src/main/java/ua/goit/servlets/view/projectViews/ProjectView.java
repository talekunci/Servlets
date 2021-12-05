package ua.goit.servlets.view.projectViews;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.model.Project;
import ua.goit.service.HandleBodyUtil;
import ua.goit.service.ProjectService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/projects/*")
public class ProjectView extends HttpServlet {

    private ProjectService service;

    @Override
    public void init() throws ServletException {
        this.service = (ProjectService) getServletContext().getAttribute("projectService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String id = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        if ("new".equalsIgnoreCase(id)) {
            req.setAttribute("project", new Project());
            req.setAttribute("isNew", true);
            req.getRequestDispatcher("/jsp/project.jsp").forward(req, resp);
        }

        Optional<Project> optional = service.get(Long.parseLong(id));
        if (optional.isPresent()) {
            Project project = optional.get();
            req.setAttribute("project", project);
            req.getRequestDispatcher("/jsp/project.jsp").forward(req, resp);
        }
        resp.sendRedirect("/projects");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        Optional<Project> modelFromStream = HandleBodyUtil.getModelFromStream(req.getInputStream(), Project.class);
//        if (modelFromStream.isPresent()) {
//            Project project = modelFromStream.get();
//            service.get(project.getId()).ifPresent(oldProject -> project.setCreationDate(oldProject.getCreationDate()));
//
//            service.update(project);
//        }

        HandleBodyUtil.getModelFromStream(req.getInputStream(), Project.class)
                .ifPresent(project -> {
                    service.get(project.getId()).ifPresent(oldProject -> project.setCreationDate(oldProject.getCreationDate()));

                    service.update(project);
                });


        resp.sendRedirect("/projects");
    }
}
