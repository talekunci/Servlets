package ua.goit.servlets.view.developerViews;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.model.*;
import ua.goit.service.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/developers/skills/add")
public class DeveloperSkillView extends HttpServlet {

    private DeveloperService developerService;
    private SkillsService skillsService;

    @Override
    public void init() throws ServletException {
        this.developerService = (DeveloperService) getServletContext().getAttribute("developerService");
        this.skillsService = (SkillsService) getServletContext().getAttribute("skillsService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String devId = req.getParameter("developerId");

        if (devId.isEmpty()) return;

        req.setAttribute("developerId", devId);

        req.getRequestDispatcher("/jsp/newDeveloperSkill.jsp").forward(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HandleBodyUtil.getModelFromStream(req.getInputStream(), PutResponse.class)
                .ifPresent(response -> developerService.get(response.getDevId())
                        .ifPresent(developer -> {
                            final String branch = response.getBranch().replaceAll("\\W+|\\d+", "");
                            final String level = response.getLevel().replaceAll("\\W+|\\d+", "");

                            List<Skill> skills = skillsService.getAll().stream().filter(s -> s.getBranch().equalsIgnoreCase(branch) &&
                                    s.getSkillLevel().equalsIgnoreCase(level)).collect(Collectors.toList());

                            final Skill skill;

                            if (!skills.isEmpty()) {
                                skill = skills.get(0);
                            } else {
                                skill = skillsService.create(new Skill(0L, branch, level)).orElse(null);
                            }

                            if (skill != null) {
                                Skill developerSkillByBranch = developer.getSkills()
                                        .stream()
                                        .filter(s -> s.getBranch().equalsIgnoreCase(skill.getBranch()))
                                        .iterator().next();

                                //Deleting previous skill by branch
                                if (developerSkillByBranch != null) {
                                    developerService.deleteSkill(developer.getId(), developerSkillByBranch.getId());
                                    developer.getSkills().removeIf(s -> s.equals(developerSkillByBranch));
                                }

                                developerService.addSkill(developer.getId(), skill.getId());
                            }

                        })
                );
    }

    private static class PutResponse {
        private Long devId;
        private String branch;
        private String level;

        public Long getDevId() {
            return devId;
        }

        public String getBranch() {
            return branch;
        }

        public String getLevel() {
            return level;
        }
    }
}
