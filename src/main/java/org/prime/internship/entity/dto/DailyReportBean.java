package org.prime.internship.entity.dto;

import java.time.LocalDate;

public class DailyReportBean {
    private String city;
    private String department;
    private String employee;
    private Double turnover;
    private String company;
    private LocalDate date;

    public DailyReportBean() {
    }

    public DailyReportBean(String city, String department, String employee, Double turnover) {
        this.city = city;
        this.department = department;
        this.employee = employee;
        this.turnover = turnover;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return  "city='" + city + '\'' +
                ", department='" + department + '\'' +
                ", employee='" + employee + '\'' +
                ", turnover=" + turnover +
                '}';
    }
}
