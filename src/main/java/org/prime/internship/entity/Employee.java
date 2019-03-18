package org.prime.internship.entity;

public class Employee {
    private int employee_id;
    private String employee_name;
    private int company_id;
    private int city_id;
    private int department_id;
    private int turnover_id;

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public int getTurnover_id() {
        return turnover_id;
    }

    public void setTurnover_id(int turnover_id) {
        this.turnover_id = turnover_id;
    }
}
