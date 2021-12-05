package ua.goit.servlets.view.customerViews;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.model.Project;
import ua.goit.service.CustomerService;
import ua.goit.service.HandleBodyUtil;
import ua.goit.service.ProjectService;

import java.io.IOException;

@WebServlet("/customers/projects/add")
public class CustomerProjectView extends HttpServlet {

    private CustomerService customerService;
    private ProjectService projectService;

    @Override
    public void init() throws ServletException {
        customerService = (CustomerService) getServletContext().getAttribute("customerService");
        projectService = (ProjectService) getServletContext().getAttribute("projectService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String customerId = req.getParameter("customerId");

        if (customerId.isEmpty()) return;

        req.setAttribute("customerId", customerId);

        req.getRequestDispatcher("/jsp/newCustomerProject.jsp").forward(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HandleBodyUtil.getModelFromStream(req.getInputStream(), PutResponse.class)
                .ifPresent(response -> customerService.get(response.getCustomerId())
                        .ifPresent(customer -> {
                            final Long projectId = response.getProjectId();

                            final Project project = projectService.getAll()
                                    .stream()
                                    .filter(p -> p.getId().compareTo(projectId) == 0)
                                    .iterator().next();

                            if (project != null) customerService.addProject(customer.getId(), projectId);
                        })
                );
    }

    private static class PutResponse {
        private Long customerId;
        private Long projectId;

        public Long getCustomerId() {
            return customerId;
        }

        public Long getProjectId() {
            return projectId;
        }
    }
}
