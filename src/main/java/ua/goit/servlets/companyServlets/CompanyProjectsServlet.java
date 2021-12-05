package ua.goit.servlets.companyServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.model.Company;
import ua.goit.service.CompanyService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/companies/projects/*")
public class CompanyProjectsServlet extends HttpServlet {

    private CompanyService service;

    @Override
    public void init() throws ServletException {
        service = (CompanyService) getServletContext().getAttribute("companyService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String URI = req.getRequestURI();
        final String companyIdString = URI.substring(URI.lastIndexOf("/") + 1);

        if (companyIdString.isEmpty()) return;

        long companyId = Long.parseLong(companyIdString);
        Optional<Company> companyOpt = service.get(companyId);

        if (companyOpt.isEmpty()) return;

        Company company = companyOpt.get();

        String deleteProjectId = req.getParameter("deleteProjectId");
        if (deleteProjectId != null) {
            long projectId = Long.parseLong(deleteProjectId);
            company.getProjects().removeIf(project -> project.getId().compareTo(projectId) == 0);
            service.deleteProject(projectId);
        }

        req.setAttribute("companyId", companyId);
        req.setAttribute("projects", company.getProjects());
        req.getRequestDispatcher("/jsp/companyProjects.jsp").forward(req, resp);
    }
}
