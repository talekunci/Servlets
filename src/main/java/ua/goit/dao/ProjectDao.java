package ua.goit.dao;

import ua.goit.dao.AbstractDao.AbstractDao;
import ua.goit.model.Project;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProjectDao extends AbstractDao<Project> {

    private final String sqlCreate = String.format("insert into %s(company_id, name, description, cost, creation_date) values(?, ?, ?, ?, ?)", getTableName());
    private final String sqlUpdate = String.format("update %s set company_id = ?, name = ?, description = ?, cost = ?, creation_date = ? where id = ?", getTableName());

    private static final String selectByCustomerId = "select * from projects p join customers_projects cp on cp.customer_id = ? and cp.project_id = p.id";
    private static final String selectByDeveloperId = "select * from projects p join project_developers pd on pd.developer_id = ? and pd.project_id = p.id";

    private static final String sqlInsertDeveloper = "insert into project_developers(project_id, developer_id) values(?, ?)";
    private static final String sqlDeleteDeveloper = "delete from project_developers where project_id = ? and developer_id = ?";

    private static ProjectDao instance;

    private ProjectDao() {
    }

    public static ProjectDao getInstance() {
        return instance == null ? instance = new ProjectDao() : instance;
    }

    public List<Project> getProjectsByCustomerId(Long customerId) {
        List<Project> result = new ArrayList<>();

        try (ResultSet set = SqlExecutor.getResultSet(selectByCustomerId, ps -> ps.setLong(1, customerId))) {

            while (set.next()) result.add(mapToEntity(set));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Project> getProjectsByDeveloperId(Long developerId) {
        List<Project> result = new ArrayList<>();

        try (ResultSet set = SqlExecutor.getResultSet(selectByDeveloperId, ps -> ps.setLong(1, developerId))) {

            while (set.next()) result.add(mapToEntity(set));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Project> getProjectsByCompanyId(Long companyId) {
        return getAll().stream().filter(project -> project.getCompanyId().compareTo(companyId) == 0).collect(Collectors.toList());
    }

    @Override
    public List<Project> getAll() {
        List<Project> projects = super.getAll();

        DeveloperDao developerDao = DeveloperDao.getInstance();
        CustomerDao customerDao = CustomerDao.getInstance();

        projects.forEach(project -> {
            project.setDevelopers(developerDao.getDevelopersByProjectId(project.getId()));
            project.setCustomers(customerDao.getCustomersByProjectId(project.getId()));
        });

        return projects;
    }

    @Override
    public Optional<Project> get(Long id) {
        Optional<Project> projectOptional = super.get(id);

        DeveloperDao developerDao = DeveloperDao.getInstance();
        CustomerDao customerDao = CustomerDao.getInstance();

        projectOptional.ifPresent(project -> {
            project.setDevelopers(developerDao.getDevelopersByProjectId(project.getId()));
            project.setCustomers(customerDao.getCustomersByProjectId(project.getId()));
        });

        return projectOptional;
    }

    @Override
    public Optional<Project> create(Project newEntity) {
        if (newEntity == null) return Optional.empty();

        SqlExecutor.execute(sqlCreate, ps -> {
            ps.setLong(1, newEntity.getCompanyId());
            ps.setString(2, newEntity.getName());
            ps.setString(3, newEntity.getDescription());
            ps.setInt(4, newEntity.getCost());
            ps.setDate(5, newEntity.getCreationDate());
        });
        return Optional.of(getAll().stream().filter(project -> project.equals(newEntity)
        ).iterator().next());
    }

    public void deleteDeveloper(Long projectId, Long developerId) {
        SqlExecutor.execute(sqlDeleteDeveloper, ps -> {
            ps.setLong(1, projectId);
            ps.setLong(2, developerId);
        });
    }

    public void addDeveloper(Long projectId, Long developerId) {
        SqlExecutor.execute(sqlInsertDeveloper, ps -> {
            ps.setLong(1, projectId);
            ps.setLong(2, developerId);
        });
    }

    @Override
    public Optional<Project> createEntity(String sql) {
        String[] params = sql.split(" ");

        Project project = new Project(Long.parseLong(params[0]), Long.parseLong(params[1]), params[2]);

        try {
            project.setDescription(params[3]);
            project.setCost(Integer.parseInt(params[4]));
            project.setCreationDate(new Date(Date.valueOf(params[5]).getTime()));
        } catch (ArrayIndexOutOfBoundsException ignore) {
        }

        return Optional.of(project);
    }

    @Override
    public int update(Project entity) {
        if (entity == null) return -1;

        return SqlExecutor.execute(sqlUpdate, ps -> {
            ps.setLong(6, entity.getId());
            ps.setLong(1, entity.getCompanyId());
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getDescription());
            ps.setInt(4, entity.getCost());
            ps.setDate(5, entity.getCreationDate());
        });
    }

    @Override
    protected String getTableName() {
        return "projects";
    }

    @Override
    protected Project mapToEntity(ResultSet resultSet) throws SQLException {
        return new Project(
                resultSet.getLong("id"),
                resultSet.getLong("company_id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getInt("cost"),
                resultSet.getDate("creation_date")
        );
    }
}
