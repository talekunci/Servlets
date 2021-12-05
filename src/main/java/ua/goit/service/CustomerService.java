package ua.goit.service;

import ua.goit.dao.CustomerDao;
import ua.goit.model.Customer;

import java.util.List;
import java.util.Optional;

public class CustomerService {

    private static CustomerService service;
    private static final CustomerDao dao = CustomerDao.getInstance();

    private CustomerService() {}


    public static synchronized CustomerService getInstance() {
        return service == null ? service = new CustomerService() : service;
    }

    public List<Customer> getAll() {
        return dao.getAll();
    }

    public Optional<Customer> get(long id) {
        return dao.get(id);
    }

    public void update(Customer customer) {
        dao.update(customer);
    }

    public void create(Customer customer) {
        dao.create(customer);
    }

    public void delete(Long id) {
        dao.delete(id);
    }

    public void addProject(Long customerId, Long projectId) {
        dao.addProject(customerId, projectId);
    }

    public void deleteProject(Long customerId, Long projectId) {
        dao.deleteProject(customerId, projectId);
    }

}
