package ua.goit.servlets.view.developerViews;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.model.Developer;
import ua.goit.service.DeveloperService;
import ua.goit.service.HandleBodyUtil;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/developers/*")
public class DeveloperViewServlet extends HttpServlet {

    private DeveloperService developerService;

    @Override
    public void init() throws ServletException {
        this.developerService = (DeveloperService) getServletContext().getAttribute("developerService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String id = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        if ("new".equalsIgnoreCase(id)) {
            req.setAttribute("developer", new Developer());
            req.setAttribute("isNew", true);
            req.getRequestDispatcher("/jsp/developer.jsp").forward(req, resp);
        }

        Optional<Developer> optional = developerService.get(Long.parseLong(id));
        if (optional.isPresent()) {
            Developer developer = optional.get();
            req.setAttribute("developer", developer);
            req.getRequestDispatcher("/jsp/developer.jsp").forward(req, resp);
        }
        resp.sendRedirect("/developers");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HandleBodyUtil.getModelFromStream(req.getInputStream(), Developer.class)
                .ifPresent(developer -> developerService.update(developer));
        resp.sendRedirect("/developers");
    }
}
