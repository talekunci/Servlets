package ua.goit.servlets.view.companyViews;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.service.CompanyService;
import ua.goit.service.DeveloperService;
import ua.goit.service.HandleBodyUtil;

import java.io.IOException;

@WebServlet("/companies/developers/add")
public class CompanyDeveloperView extends HttpServlet {

    private CompanyService companyService;
    private DeveloperService developerService;

    @Override
    public void init() throws ServletException {
        companyService = (CompanyService) getServletContext().getAttribute("companyService");
        developerService = (DeveloperService) getServletContext().getAttribute("developerService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String companyId = req.getParameter("companyId");

        if (companyId.isEmpty()) return;

        req.setAttribute("companyId", companyId);

        req.getRequestDispatcher("/jsp/newCompanyDeveloper.jsp").forward(req, resp);
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HandleBodyUtil.getModelFromStream(req.getInputStream(), PutResponse.class)
                .ifPresent(response -> companyService.get(response.companyId)
                        .ifPresent(company -> {
                            final long developerId = response.devId;

                            if (developerService.get(developerId).isPresent())
                                companyService.addDeveloper(company.getId(), developerId);
                        })
                );
    }

    private static class PutResponse {
        private long companyId;
        private Long devId;

        public Long getDevId() {
            return devId;
        }

        public long getCompanyId() {
            return companyId;
        }
    }

}
