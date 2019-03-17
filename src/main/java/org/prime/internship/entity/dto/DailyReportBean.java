package org.prime.internship.entity.dto;

import java.time.LocalDate;

public class DailyReportBean {
    private String companyName;
    private LocalDate date;
    private String city;
    private String department;
    private String employee;
    private Double turnover;

    public DailyReportBean() {
    }

    public DailyReportBean(String companyName, LocalDate date, String city, String department, String employee, Double turnover) {
        this.companyName = companyName;
        this.date = date;
        this.city = city;
        this.department = department;
        this.employee = employee;
        this.turnover = turnover;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
        return "DailyReportBean{" +
                "companyName='" + companyName + '\'' +
                ", date=" + date +
                ", city='" + city + '\'' +
                ", department='" + department + '\'' +
                ", employee='" + employee + '\'' +
                ", turnover=" + turnover +
                '}';
    }
}