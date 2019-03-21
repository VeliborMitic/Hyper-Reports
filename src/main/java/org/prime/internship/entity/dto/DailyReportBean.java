package org.prime.internship.entity.dto;

import java.time.LocalDate;

public class DailyReportBean {
    private String city;
    private String department;
    private String employee;
    private Double turnover;

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

    @Override
    public String toString() {
        return  "city='" + city + '\'' +
                ", department='" + department + '\'' +
                ", employee='" + employee + '\'' +
                ", turnover=" + turnover +
                '}';
    }
}
