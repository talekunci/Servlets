package ua.goit.service;

import ua.goit.dao.CompanyDao;
import ua.goit.model.Company;

import java.util.List;
import java.util.Optional;

public class CompanyService {

    private static CompanyService service;
    private static final CompanyDao dao = CompanyDao.getInstance();

    private CompanyService() {}


    public static synchronized CompanyService getInstance() {
        return service == null ? service = new CompanyService() : service;
    }

    public List<Company> getAll() {
        return dao.getAll();
    }

    public Optional<Company> get(long id) {
        return dao.get(id);
    }

    public void update(Company company) {
        dao.update(company);
    }

    public Optional<Company> create(Company company) {
        return dao.create(company);
    }

    public void delete(Long id) {
        dao.delete(id);
    }

    public void deleteDeveloper(Long companyId, Long developerId) {
        dao.deleteDeveloper(companyId, developerId);
    }

    public void addDeveloper(Long companyId, Long developerId) {
        dao.addDeveloper(companyId, developerId);
    }

    public void deleteProject(Long projectId) {
        dao.deleteProject(projectId);
    }

}
