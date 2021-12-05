package ua.goit.service;

import ua.goit.dao.ProjectDao;
import ua.goit.model.Project;

import java.util.List;
import java.util.Optional;

public class ProjectService {

    private static ProjectService service;
    private static final ProjectDao dao = ProjectDao.getInstance();

    private ProjectService() {}


    public static synchronized ProjectService getInstance() {
        return service == null ? service = new ProjectService() : service;
    }

    public List<Project> getAll() {
        return dao.getAll();
    }

    public Optional<Project> get(long id) {
        return dao.get(id);
    }

    public void update(Project project) {
        dao.update(project);
    }

    public void create(Project project) {
        dao.create(project);
    }

    public void delete(Long id) {
        dao.delete(id);
    }

    public void deleteDeveloper(Long projectId, Long developerId) {
        dao.deleteDeveloper(projectId, developerId);
    }

    public void addDeveloper(Long projectId, Long developerId) {
        dao.addDeveloper(projectId, developerId);
    }

}
