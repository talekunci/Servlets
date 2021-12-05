package ua.goit.servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ua.goit.dao.*;
import ua.goit.service.*;

@WebListener
public class LoadListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        servletContext.setAttribute("developerDao", DeveloperDao.getInstance());
        servletContext.setAttribute("projectDao", ProjectDao.getInstance());
        servletContext.setAttribute("companyDao", CompanyDao.getInstance());
        servletContext.setAttribute("customerDao", CustomerDao.getInstance());

        servletContext.setAttribute("developerService", DeveloperService.getInstance());
        servletContext.setAttribute("projectService", ProjectService.getInstance());
        servletContext.setAttribute("companyService", CompanyService.getInstance());
        servletContext.setAttribute("customerService", CustomerService.getInstance());
        servletContext.setAttribute("skillsService", SkillsService.getInstance());
    }

}
