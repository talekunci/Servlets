package ua.goit.servlets.view.developerViews;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.model.*;
import ua.goit.service.*;

import java.io.IOException;

@WebServlet("/developers/companies/add")
public class DeveloperCompanyView extends HttpServlet {

    private DeveloperService developerService;
    private CompanyService companyService;

    @Override
    public void init() throws ServletException {
        this.developerService = (DeveloperService) getServletContext().getAttribute("developerService");
        this.companyService = (CompanyService) getServletContext().getAttribute("companyService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String devId = req.getParameter("developerId");

        if (devId.isEmpty()) return;

        req.setAttribute("developerId", devId);

        req.getRequestDispatcher("/jsp/newDeveloperCompany.jsp").forward(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HandleBodyUtil.getModelFromStream(req.getInputStream(), PutResponse.class)
                .ifPresent(response -> developerService.get(response.getDevId())
                        .ifPresent(developer -> {
                            final String companyName = response.getCompanyName();

                            final Company company = companyService.getAll()
                                    .stream()
                                    .filter(c -> c.getName().equalsIgnoreCase(companyName))
                                    .iterator().next();

                            if (company != null) developerService.addCompany(developer.getId(), company.getId());
                        })
                );
    }

    private static class PutResponse {
        private Long devId;
        private String companyName;

        public Long getDevId() {
            return devId;
        }

        public String getCompanyName() {
            return companyName;
        }
    }
}
