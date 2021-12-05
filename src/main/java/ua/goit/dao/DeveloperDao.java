package ua.goit.dao;

import ua.goit.dao.AbstractDao.AbstractDao;
import ua.goit.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeveloperDao extends AbstractDao<Developer> {

    private final String sqlCreate = String.format("insert into %s(name, age, gender, description, salary) values(?, ?, ?, ?, ?)", getTableName());
    private final String sqlUpdate = String.format("update %s set name = ?, age = ?, gender = ?, description = ?, salary = ? where id = ?", getTableName());

    private static final String selectByCompanyId = "select * from developers d join developer_companies dc on dc.company_id = ? and dc.developer_id = d.id";
    private static final String selectByProjectId = "select * from developers d join project_developers pd on pd.project_id = ? and pd.developer_id = d.id";

    private static final String sqlInsertSkill = "insert into developer_skills(developer_id, skill_id) values(?, ?)";
    private static final String sqlSelectSkills = "select skill_id from developer_skills where developer_id = ?";
    private static final String sqlDeleteSkill = "delete from developer_skills where developer_id = ? and skill_id = ?";

    private static final String sqlInsertCompany = "insert into developer_companies(developer_id, company_id) values(?, ?)";
    private static final String sqlDeleteCompany = "delete from developer_companies where developer_id = ? and company_id = ?";

    private static final String sqlInsertProject = "insert into project_developers(developer_id, project_id) values(?, ?)";
    private static final String sqlDeleteProject = "delete from project_developers where developer_id = ? and project_id = ?";

    private static DeveloperDao instance;

    private DeveloperDao() {
    }

    public static DeveloperDao getInstance() {
        return instance == null ? instance = new DeveloperDao() : instance;
    }

    public List<Developer> getDevelopersByProjectId(Long projectId) {
        List<Developer> result = new ArrayList<>();

        try (ResultSet resultSet = SqlExecutor.getResultSet(selectByProjectId, ps -> ps.setLong(1, projectId))) {

            while (resultSet.next()) {
                result.add(mapToEntity(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Developer> getDevelopersByCompanyId(Long companyId) {
        List<Developer> result = new ArrayList<>();

        try (ResultSet resultSet = SqlExecutor.getResultSet(selectByCompanyId, ps -> ps.setLong(1, companyId))) {

            while (resultSet.next()) {
                result.add(mapToEntity(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Developer> getAll() {
        List<Developer> all = super.getAll();

        SkillDao skillDao = SkillDao.getInstance();
        ProjectDao projectDao = ProjectDao.getInstance();
        CompanyDao companyDao = CompanyDao.getInstance();

        all.forEach(developer -> {
            try (ResultSet resultSet = SqlExecutor.getResultSet(sqlSelectSkills,
                    ps -> ps.setLong(1, developer.getId()))) {

                List<Skill> skills = developer.getSkills();

                while (resultSet.next()) skillDao.get(resultSet.getLong("skill_id")).ifPresent(skills::add);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            developer.setCompanies(companyDao.getByDeveloperId(developer.getId()));
            developer.setProjects(projectDao.getProjectsByDeveloperId(developer.getId()));
        });

        return all;
    }

    @Override
    public Optional<Developer> get(Long id) {
        Optional<Developer> result = super.get(id);

        SkillDao skillDao = SkillDao.getInstance();

        result.ifPresent(developer -> {
            try (ResultSet resultSet = SqlExecutor.getResultSet(sqlSelectSkills,
                    ps -> ps.setLong(1, developer.getId()))) {

                List<Skill> skills = developer.getSkills();

                while (resultSet.next()) skillDao.get(resultSet.getLong("skill_id")).ifPresent(skills::add);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            developer.setCompanies(CompanyDao.getInstance().getByDeveloperId(developer.getId()));
            developer.setProjects(ProjectDao.getInstance().getProjectsByDeveloperId(developer.getId()));
        });

        return result;
    }

    @Override
    public Optional<Developer> create(Developer newEntity) {
        if (newEntity == null) return Optional.empty();

        SqlExecutor.execute(sqlCreate, ps -> {
            ps.setString(1, newEntity.getName());
            ps.setInt(2, newEntity.getAge());
            ps.setString(3, newEntity.getGender());
            ps.setString(4, newEntity.getDescription());
            ps.setInt(5, newEntity.getSalary());
        });

        return Optional.of(getAll().stream().filter(developer -> developer.equals(newEntity)
        ).iterator().next());
    }

    public void deleteSkill(Long developerId, Long skillId) {
        SqlExecutor.execute(sqlDeleteSkill, ps -> {
            ps.setLong(1, developerId);
            ps.setLong(2, skillId);
        });
    }

    public void deleteProject(Long developerId, Long projectId) {
        SqlExecutor.execute(sqlDeleteProject, ps -> {
            ps.setLong(1, developerId);
            ps.setLong(2, projectId);
        });
    }

    public void deleteCompany(Long developerId, Long companyId) {
        SqlExecutor.execute(sqlDeleteCompany, ps -> {
            ps.setLong(1, developerId);
            ps.setLong(2, companyId);
        });
    }

    public void addSkill(Long developerId, Long skillId) {
        SqlExecutor.execute(sqlInsertSkill, ps -> {
            ps.setLong(1, developerId);
            ps.setLong(2, skillId);
        });
    }

    public void addProject(Long developerId, Long projectId) {
        SqlExecutor.execute(sqlInsertProject, ps -> {
            ps.setLong(1, developerId);
            ps.setLong(2, projectId);
        });
    }

    public void addCompany(Long developerId, Long companyId) {
        SqlExecutor.execute(sqlInsertCompany, ps -> {
            ps.setLong(1, developerId);
            ps.setLong(2, companyId);
        });
    }

    @Override
    public Optional<Developer> createEntity(String sql) {
        String[] params = sql.split(" ");

        Developer dev = new Developer(Long.parseLong(params[0]), params[1]);

        try {
            dev.setAge(Integer.parseInt(params[2]));
            dev.setGender(params[3]);
            dev.setDescription(params[4]);
            dev.setSalary(Integer.parseInt(params[5]));
        } catch (ArrayIndexOutOfBoundsException
                | NumberFormatException ignored) {
        }

        return Optional.of(dev);
    }

    @Override
    public int update(Developer entity) {
        if (entity == null) return -1;

        return SqlExecutor.execute(sqlUpdate, ps -> {
            ps.setLong(6, entity.getId());
            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getAge());
            ps.setString(3, entity.getGender());
            ps.setString(4, entity.getDescription());
            ps.setInt(5, entity.getSalary());
        });
    }

    @Override
    protected String getTableName() {
        return "developers";
    }

    @Override
    protected Developer mapToEntity(ResultSet resultSet) throws SQLException {
        return new Developer(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("age"),
                resultSet.getString("gender"),
                resultSet.getString("description"),
                resultSet.getInt("salary")
        );
    }
}
