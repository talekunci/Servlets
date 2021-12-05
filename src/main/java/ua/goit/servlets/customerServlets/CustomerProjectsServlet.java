package ua.goit.servlets.customerServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.model.Customer;
import ua.goit.service.CustomerService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/customers/projects/*")
public class CustomerProjectsServlet extends HttpServlet {

    private CustomerService service;

    @Override
    public void init() throws ServletException {
        service = (CustomerService) getServletContext().getAttribute("customerService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String URI = req.getRequestURI();
        final String customerIdString = URI.substring(URI.lastIndexOf("/") + 1);

        if (customerIdString.isEmpty()) return;

        long customerId = Long.parseLong(customerIdString);
        Optional<Customer> customerOpt = service.get(customerId);

        if (customerOpt.isEmpty()) return;

        Customer customer = customerOpt.get();

        String deleteProjectId = req.getParameter("deleteProjectId");
        if (deleteProjectId != null) {
            long projectId = Long.parseLong(deleteProjectId);
            customer.getProjects().removeIf(p -> p.getId().compareTo(projectId) == 0);
            service.deleteProject(customerId, projectId);
        }

        req.setAttribute("customerId", customerId);
        req.setAttribute("projects", customer.getProjects());
        req.getRequestDispatcher("/jsp/customerProjects.jsp").forward(req, resp);
    }
}
