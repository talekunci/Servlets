package ua.goit.service;

import ua.goit.dao.SkillDao;
import ua.goit.model.Skill;

import java.util.List;
import java.util.Optional;

public class SkillsService {

    private static SkillsService service;
    private static final SkillDao dao = SkillDao.getInstance();

    private SkillsService() {
    }


    public static synchronized SkillsService getInstance() {
        return service == null ? service = new SkillsService() : service;
    }

    public List<Skill> getAll() {
        return dao.getAll();
    }

    public void update(Skill skill) {
        dao.update(skill);
    }

    public Optional<Skill> create(Skill skill) {
        return dao.create(skill);
    }

}
