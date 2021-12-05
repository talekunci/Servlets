package ua.goit.servlets.developerServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.model.Developer;
import ua.goit.service.DeveloperService;
import ua.goit.service.HandleBodyUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/developers")
public class DevelopersServlet extends HttpServlet {

    private DeveloperService service;

    @Override
    public void init() throws ServletException {
        this.service = (DeveloperService) getServletContext().getAttribute("developerService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deleteId = req.getParameter("deleteId");
        if (deleteId != null) {
            service.delete(Long.parseLong(deleteId));
            resp.sendRedirect("/developers");
        } else {
            List<Developer> all = service.getAll();
            req.setAttribute("developers", all);
            req.getRequestDispatcher("/jsp/developers.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HandleBodyUtil.getModelFromStream(req.getInputStream(), Developer.class)
                .ifPresent(user -> service.create(user));
        resp.sendRedirect("/projects");
    }
}
