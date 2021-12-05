package ua.goit.dao;

import ua.goit.dao.AbstractDao.AbstractDao;
import ua.goit.model.Company;
import ua.goit.model.Developer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CompanyDao extends AbstractDao<Company> {

    private final String sqlCreate = String.format("insert into %s(name, description) values(?, ?)", getTableName());
    private final String sqlUpdate = String.format("update %s set name = ?, description = ? where id = ?", getTableName());

    private static final String selectByDeveloperId = "select * from companies c join developer_companies dc on dc.developer_id = ? and dc.company_id = c.id";

    private static final String sqlInsertDevelopers = "insert into developer_companies(company_id, developer_id) values(?, ?)";
    private static final String sqlSelectDevelopers = "select developer_id from developer_companies where company_id = ?";
    private static final String sqlDeleteDeveloper = "delete from developer_companies where company_id = ? and developer_id = ?";

    private static CompanyDao instance;

    private CompanyDao() {
    }

    public static CompanyDao getInstance() {
        return instance == null ? instance = new CompanyDao() : instance;
    }

    public List<Company> getByDeveloperId(Long developerId) {
        List<Company> result = new ArrayList<>();

        try (ResultSet set = SqlExecutor.getResultSet(selectByDeveloperId, ps -> ps.setLong(1, developerId))) {

            while (set.next()) result.add(mapToEntity(set));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Company> getAll() {
        List<Company> companyList = super.getAll();

        DeveloperDao developerDao = DeveloperDao.getInstance();
        ProjectDao projectDao = ProjectDao.getInstance();

        for (Company company : companyList) {
            company.setDevelopers(developerDao.getDevelopersByCompanyId(company.getId()));
            company.setProjects(projectDao.getProjectsByCompanyId(company.getId()));
        }

        return companyList;
    }

    @Override
    public Optional<Company> get(Long id) {
        Optional<Company> companyOptional = super.get(id);

        companyOptional.ifPresent(company -> {
            company.setDevelopers(DeveloperDao.getInstance().getDevelopersByCompanyId(company.getId()));
            company.setProjects(ProjectDao.getInstance().getProjectsByCompanyId(company.getId()));
        });

        return companyOptional;
    }

    @Override
    public Optional<Company> create(Company newEntity) {
        if (newEntity == null) return Optional.empty();

        SqlExecutor.execute(sqlCreate, ps -> {
            ps.setString(1, newEntity.getName());
            ps.setString(2, newEntity.getDescription());
        });

        return Optional.of(getAll().stream().filter(company -> company.equals(newEntity)
        ).iterator().next());
    }

    @Override
    public Optional<Company> createEntity(String sql) {
        String[] params = sql.split(" ");

        Company company = new Company(Long.parseLong(params[0]), params[1]);

        try {
            company.setDescription(params[2]);
        } catch (ArrayIndexOutOfBoundsException ignore) {
        }

        return Optional.of(company);
    }

    public List<Developer> getDevelopers(Long company_id) {
        List<Developer> developers = new ArrayList<>();

        try (ResultSet result = SqlExecutor.getResultSet(sqlSelectDevelopers, ps -> ps.setLong(1, company_id))) {
            DeveloperDao developerDao = DeveloperDao.getInstance();

            while (result.next()) developerDao.get(result.getLong("developer_id")).ifPresent(developers::add);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return developers;
    }

    public void addDeveloper(Long company_id, Long developerId) {
        SqlExecutor.execute(sqlInsertDevelopers, ps -> {
            ps.setLong(1, company_id);
            ps.setLong(2, developerId);
        });
    }

    public void deleteDeveloper(Long company_id, Long developerId) {
        SqlExecutor.execute(sqlDeleteDeveloper, ps -> {
            ps.setLong(1, company_id);
            ps.setLong(2, developerId);
        });
    }

    public void deleteProject(Long projectId) {
        ProjectDao.getInstance().delete(projectId);
    }

    @Override
    public int update(Company entity) {
        if (entity == null) return -1;

        return SqlExecutor.execute(sqlUpdate, ps -> {
            ps.setLong(3, entity.getId());
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
        });
    }

    @Override
    protected String getTableName() {
        return "companies";
    }

    @Override
    protected Company mapToEntity(ResultSet resultSet) throws SQLException {
        return new Company(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description")
        );
    }
}
