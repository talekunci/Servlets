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

@WebServlet("/developers/companies/*")
public class DeveloperCompaniesServlet extends HttpServlet {

    private static DeveloperService service;

    @Override
    public void init() throws ServletException {
        service = (DeveloperService) getServletContext().getAttribute("developerService");
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

        String deleteCompanyId = req.getParameter("deleteCompanyId");
        if (deleteCompanyId != null) {
            long companyId = Long.parseLong(deleteCompanyId);
            developer.getCompanies().removeIf(c -> c.getId().compareTo(companyId) == 0);
            service.deleteCompany(devId, companyId);
        }

        req.setAttribute("developerId", devId);
        req.setAttribute("companies", developer.getCompanies());
        req.getRequestDispatcher("/jsp/developerCompanies.jsp").forward(req, resp);
    }

}
