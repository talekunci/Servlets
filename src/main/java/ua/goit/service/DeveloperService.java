package ua.goit.service;

import ua.goit.dao.DeveloperDao;
import ua.goit.model.Developer;

import java.util.List;
import java.util.Optional;

public class DeveloperService {

    private static DeveloperService service;
    private static final DeveloperDao dao = DeveloperDao.getInstance();

    private DeveloperService() {}


    public static synchronized DeveloperService getInstance() {
        return service == null ? service = new DeveloperService() : service;
    }



    public List<Developer> getAll() {
        return dao.getAll();
    }

    public Optional<Developer> get(long id) {
        return dao.get(id);
    }

    public void update(Developer developer) {
        dao.update(developer);
    }

    public void create(Developer developer) {
        dao.create(developer);
    }

    public void delete(Long id) {
        dao.delete(id);
    }

    public void deleteSkill(Long developerId, Long skillId) {
        dao.deleteSkill(developerId, skillId);
    }

    public void deleteProject(Long developerId, Long projectId) {
        dao.deleteProject(developerId, projectId);
    }

    public void deleteCompany(Long developerId, Long companyId) {
        dao.deleteCompany(developerId, companyId);
    }

    public void addSkill(Long developerId, Long skillId) {
        dao.addSkill(developerId, skillId);
    }

    public void addProject(Long developerId, Long projectId) {
        dao.addProject(developerId, projectId);
    }

    public void addCompany(Long developerId, Long companyId) {
        dao.addCompany(developerId, companyId);
    }

}
