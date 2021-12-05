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

@WebServlet("/companies/developers/*")
public class CompanyDevelopersServlet extends HttpServlet {

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

        String deleteDeveloperId = req.getParameter("deleteDeveloperId");
        if (deleteDeveloperId != null) {
            long developerId = Long.parseLong(deleteDeveloperId);
            company.getDevelopers().removeIf(developer -> developer.getId().compareTo(developerId) == 0);
            service.deleteDeveloper(companyId, developerId);
        }

        req.setAttribute("companyId", companyId);
        req.setAttribute("developers", company.getDevelopers());
        req.getRequestDispatcher("/jsp/companyDevelopers.jsp").forward(req, resp);
    }
}
