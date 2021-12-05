package ua.goit.servlets.view.projectViews;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.model.Developer;
import ua.goit.service.DeveloperService;
import ua.goit.service.HandleBodyUtil;
import ua.goit.service.ProjectService;

import java.io.IOException;

@WebServlet("/projects/developers/add")
public class ProjectDevelopersView extends HttpServlet {

    private ProjectService projectService;
    private DeveloperService developerService;

    @Override
    public void init() throws ServletException {
        this.projectService = (ProjectService) getServletContext().getAttribute("projectService");
        this.developerService = (DeveloperService) getServletContext().getAttribute("developerService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String projectId = req.getParameter("projectId");

        if (projectId.isEmpty()) return;

        req.setAttribute("projectId", projectId);

        req.getRequestDispatcher("/jsp/newProjectDeveloper.jsp").forward(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HandleBodyUtil.getModelFromStream(req.getInputStream(), PutResponse.class)
                .ifPresent(response -> projectService.get(response.getProjectId())
                        .ifPresent(project -> {
                            final Long developerId = response.getDevId();

                            final Developer developer = developerService.getAll()
                                    .stream()
                                    .filter(d -> d.getId().compareTo(developerId) == 0)
                                    .iterator().next();

                            if (developer != null) projectService.addDeveloper(project.getId(), developerId);
                        })
                );
    }

    private static class PutResponse {
        private Long projectId;
        private Long devId;

        public Long getDevId() {
            return devId;
        }

        public Long getProjectId() {
            return projectId;
        }
    }
}
