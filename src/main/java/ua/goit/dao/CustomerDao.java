package ua.goit.dao;

import ua.goit.dao.AbstractDao.AbstractDao;
import ua.goit.model.Customer;
import ua.goit.model.Developer;
import ua.goit.model.Project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDao extends AbstractDao<Customer> {

    private final String sqlCreate = String.format("insert into %s(name, description) values(?, ?)", getTableName());
    private final String sqlUpdate = String.format("update %s set name = ?, description = ? where id = ?", getTableName());

    private static final String selectByProjectId = "select * from customers c join customers_projects cp on cp.project_id = ? and cp.customer_id = c.id";

    private static final String sqlInsertProjects = "insert into customers_projects(customer_id, project_id) values(?, ?)";
    private static final String sqlSelectProjects = "select project_id from customers_projects where customer_id = ?";
    private static final String sqlDeleteProject = "delete from customers_projects where customer_id = ? and project_id = ?";


    private static CustomerDao instance;

    private CustomerDao() {
    }

    public static CustomerDao getInstance() {
        return instance == null ? instance = new CustomerDao() : instance;
    }

    public List<Customer> getCustomersByProjectId(Long projectId) {
        List<Customer> result = new ArrayList<>();

        try (ResultSet set = SqlExecutor.getResultSet(selectByProjectId, ps -> ps.setLong(1, projectId))) {

            while (set.next()) result.add(mapToEntity(set));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> all = super.getAll();

        ProjectDao projectDao = ProjectDao.getInstance();
        all.forEach(customer -> customer.setProjects(projectDao.getProjectsByCustomerId(customer.getId())));

        return all;
    }

    @Override
    public Optional<Customer> get(Long id) {
        Optional<Customer> result = super.get(id);

        result.ifPresent(customer -> customer.setProjects(ProjectDao.getInstance().getProjectsByCustomerId(customer.getId())));

        return result;
    }

    @Override
    public Optional<Customer> create(Customer newEntity) {
        if (newEntity == null) return Optional.empty();

        SqlExecutor.execute(sqlCreate, ps -> {
            ps.setString(1, newEntity.getName());
            ps.setString(2, newEntity.getDescription());
        });

        return Optional.of(getAll().stream().filter(customer -> customer.equals(newEntity)
        ).iterator().next());
    }

    public List<Project> getProjects(Long customerId) {
        List<Project> projects = new ArrayList<>();

        try (ResultSet result = SqlExecutor.getResultSet(sqlSelectProjects, ps -> ps.setLong(1, customerId))) {
            ProjectDao projectDao = ProjectDao.getInstance();

            while (result.next()) projectDao.get(result.getLong("project_id")).ifPresent(projects::add);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }

    public void addProject(Long customerId, Long projectId) {
        SqlExecutor.execute(sqlInsertProjects, ps -> {
            ps.setLong(1, customerId);
            ps.setLong(2, projectId);
        });
    }

    public void deleteProject(Long customerId, Long projectId) {
        SqlExecutor.execute(sqlDeleteProject, ps -> {
            ps.setLong(1, customerId);
            ps.setLong(2, projectId);
        });
    }

    @Override
    public Optional<Customer> createEntity(String sql) {
        String[] params = sql.split(" ");

        Customer customer = new Customer(0L, params[1]);

        try {
            customer.setDescription(params[2]);
        } catch (ArrayIndexOutOfBoundsException ignore) {}


        return Optional.of(customer);
    }

    @Override
    public int update(Customer entity) {
        if (entity == null) return -1;

        return SqlExecutor.execute(sqlUpdate, ps -> {
            ps.setLong(3, entity.getId());
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
        });
    }

    @Override
    protected String getTableName() {
        return "customers";
    }

    @Override
    protected Customer mapToEntity(ResultSet resultSet) throws SQLException {
        return new Customer(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description")
        );
    }
}
