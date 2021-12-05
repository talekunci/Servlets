package ua.goit.servlets.view.companyViews;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.model.Company;
import ua.goit.service.CompanyService;
import ua.goit.service.HandleBodyUtil;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/companies/*")
public class CompanyViewServlet extends HttpServlet {

    private CompanyService service;

    @Override
    public void init() throws ServletException {
        this.service = (CompanyService) getServletContext().getAttribute("companyService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String id = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        if ("new".equalsIgnoreCase(id)) {
            req.setAttribute("company", new Company());
            req.setAttribute("isNew", true);
            req.getRequestDispatcher("/jsp/company.jsp").forward(req, resp);
        }

        Optional<Company> optional = service.get(Long.parseLong(id));
        if (optional.isPresent()) {
            Company company = optional.get();
            req.setAttribute("company", company);
            req.getRequestDispatcher("/jsp/company.jsp").forward(req, resp);
        }
        resp.sendRedirect("/companies");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HandleBodyUtil.getModelFromStream(req.getInputStream(), Company.class)
                .ifPresent(company -> service.update(company));
        resp.sendRedirect("/companies");
    }
}
