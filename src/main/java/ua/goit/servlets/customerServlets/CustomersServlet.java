package ua.goit.servlets.customerServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.model.Customer;
import ua.goit.service.CustomerService;
import ua.goit.service.HandleBodyUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/customers")
public class CustomersServlet extends HttpServlet {

    private CustomerService service;

    @Override
    public void init() throws ServletException {
        this.service = (CustomerService) getServletContext().getAttribute("customerService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deleteId = req.getParameter("deleteId");
        if (deleteId != null) {
            service.delete(Long.parseLong(deleteId));
            resp.sendRedirect("/customers");
        } else {
            List<Customer> all = service.getAll();
            req.setAttribute("customers", all);
            req.getRequestDispatcher("/jsp/customers.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HandleBodyUtil.getModelFromStream(req.getInputStream(), Customer.class)
                .ifPresent(customer -> service.create(customer));
        resp.sendRedirect("/customers");
    }
}
