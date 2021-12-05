package ua.goit.servlets.projectServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.model.Project;
import ua.goit.service.ProjectService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/projects/developers/*")
public class ProjectDevelopersServlet extends HttpServlet {

    private static ProjectService service;

    @Override
    public void init() throws ServletException {
        service = (ProjectService) getServletContext().getAttribute("projectService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String URI = req.getRequestURI();
        final String projectIdString = URI.substring(URI.lastIndexOf("/") + 1);

        if (projectIdString.isEmpty()) return;

        long projectId = Long.parseLong(projectIdString);
        Optional<Project> projectOptional = service.get(projectId);

        if (projectOptional.isEmpty()) return;

        Project project = projectOptional.get();

        String deleteDeveloperId = req.getParameter("deleteDeveloperId");
        if (deleteDeveloperId != null) {
            long developerId = Long.parseLong(deleteDeveloperId);
            project.getDevelopers().removeIf(d -> d.getId().compareTo(developerId) == 0);
            service.deleteDeveloper(projectId, developerId);
        }

        req.setAttribute("projectId", projectId);
        req.setAttribute("developers", project.getDevelopers());
        req.getRequestDispatcher("/jsp/projectDevelopers.jsp").forward(req, resp);
    }
}
