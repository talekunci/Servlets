package ua.goit.servlets.view.developerViews;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.model.Project;
import ua.goit.service.*;

import java.io.IOException;

@WebServlet("/developers/projects/add")
public class DeveloperProjectView extends HttpServlet {

    private DeveloperService developerService;
    private ProjectService projectService;

    @Override
    public void init() throws ServletException {
        this.developerService = (DeveloperService) getServletContext().getAttribute("developerService");
        this.projectService = (ProjectService) getServletContext().getAttribute("projectService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String devId = req.getParameter("developerId");

        if (devId.isEmpty()) return;

        req.setAttribute("developerId", devId);

        req.getRequestDispatcher("/jsp/newDeveloperProject.jsp").forward(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HandleBodyUtil.getModelFromStream(req.getInputStream(), PutResponse.class)
                .ifPresent(response -> developerService.get(response.getDevId())
                        .ifPresent(developer -> {
                            final Long projectId = response.getProjectId();

                            final Project project = projectService.getAll()
                                    .stream()
                                    .filter(p -> p.getId().compareTo(projectId) == 0)
                                    .iterator().next();

                            if (project != null) developerService.addProject(developer.getId(), projectId);
                        })
                );
    }

    private static class PutResponse {
        private Long devId;
        private Long projectId;

        public Long getDevId() {
            return devId;
        }

        public Long getProjectId() {
            return projectId;
        }
    }
}

