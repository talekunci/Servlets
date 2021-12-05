package ua.goit.servlets.view.customerViews;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.model.Customer;
import ua.goit.service.CustomerService;
import ua.goit.service.HandleBodyUtil;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/customers/*")
public class CustomerViewServlet extends HttpServlet {

    private CustomerService service;

    @Override
    public void init() throws ServletException {
        this.service = (CustomerService) getServletContext().getAttribute("customerService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String id = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        if ("new".equalsIgnoreCase(id)) {
            req.setAttribute("customer", new Customer());
            req.setAttribute("isNew", true);
            req.getRequestDispatcher("/jsp/customer.jsp").forward(req, resp);
        }

        Optional<Customer> optional = service.get(Long.parseLong(id));
        if (optional.isPresent()) {
            Customer customer = optional.get();
            req.setAttribute("customer", customer);
            req.getRequestDispatcher("/jsp/customer.jsp").forward(req, resp);
        }
        resp.sendRedirect("/customers");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HandleBodyUtil.getModelFromStream(req.getInputStream(), Customer.class)
                .ifPresent(customer -> service.update(customer));
        resp.sendRedirect("/customers");
    }
}
