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

@WebServlet("/developers/skills/*")
public class DeveloperSkillsServlet extends HttpServlet {

    private DeveloperService service;

    @Override
    public void init() throws ServletException {
        this.service = (DeveloperService) getServletContext().getAttribute("developerService");
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

        String deleteSkillId = req.getParameter("deleteSkillId");
        if (deleteSkillId != null) {
            long skillId = Long.parseLong(deleteSkillId);
            developer.getSkills().removeIf(s -> s.getId().compareTo(skillId) == 0);
            service.deleteSkill(devId, skillId);
        }

        req.setAttribute("developerId", devId);
        req.setAttribute("skills", developer.getSkills());
        req.getRequestDispatcher("/jsp/developerSkills.jsp").forward(req, resp);
    }

}
