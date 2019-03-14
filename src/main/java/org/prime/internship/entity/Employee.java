package org.prime.internship.entity;


import java.util.List;

public class Employee {
    private int id;
    private String name;
    private Company company;
    private City city;
    private Department department;
    private List<Turnover> turnovers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Turnover> getTurnovers() {
        return turnovers;
    }

    public void setTurnovers(List<Turnover> turnovers) {
        this.turnovers = turnovers;
    }
}
