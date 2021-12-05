package ua.goit.servlets.developerServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.model.Developer;
import ua.goit.service.DeveloperService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/developers/projects/*")
public class DeveloperProjectsServlet extends HttpServlet {

    private DeveloperService service;

    @Override
    public void init() throws ServletException {
        this.service = (DeveloperService) getServletContext().getAttribute("developerService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String URI = req.getRequestURI();
        final String devIdString = URI.substring(URI.lastIndexOf("/") + 1);

        if (devIdString.isEmpty()) return;

        long devId = Long.parseLong(devIdString);
        Optional<Developer> developerOpt = service.get(devId);

        if (developerOpt.isEmpty()) return;

        Developer developer = developerOpt.get();

        String deleteProjectId = req.getParameter("deleteProjectId");
        if (deleteProjectId != null) {
            long projectId = Long.parseLong(deleteProjectId);
            developer.getProjects().removeIf(p -> p.getId().compareTo(projectId) == 0);
            service.deleteProject(devId, projectId);
        }

        req.setAttribute("developerId", devId);
        req.setAttribute("projects", developer.getProjects());
        req.getRequestDispatcher("/jsp/developerProjects.jsp").forward(req, resp);
    }

}
