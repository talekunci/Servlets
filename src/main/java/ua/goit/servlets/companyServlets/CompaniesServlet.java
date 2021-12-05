package ua.goit.servlets.companyServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.model.Company;
import ua.goit.service.CompanyService;
import ua.goit.service.HandleBodyUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/companies")
public class CompaniesServlet extends HttpServlet {

    private CompanyService service;

    @Override
    public void init() throws ServletException {
        this.service = (CompanyService) getServletContext().getAttribute("companyService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deleteId = req.getParameter("deleteId");
        if (deleteId != null) {
            service.delete(Long.parseLong(deleteId));
            resp.sendRedirect("/companies");
        } else {
            List<Company> all = service.getAll();
            req.setAttribute("companies", all);
            req.getRequestDispatcher("/jsp/companies.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HandleBodyUtil.getModelFromStream(req.getInputStream(), Company.class)
                .ifPresent(company -> service.create(company));
        resp.sendRedirect("/projects");
    }
}
