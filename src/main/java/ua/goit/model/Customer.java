package ua.goit.model;

import ua.goit.dao.AbstractDao.Identity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer implements Identity {
    private Long id;
    private String name;
    private String description;

    private List<Project> projects = new ArrayList<>();

    public Customer() {}

    public Customer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Customer(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getName(), customer.getName()) && Objects.equals(getDescription(), customer.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
