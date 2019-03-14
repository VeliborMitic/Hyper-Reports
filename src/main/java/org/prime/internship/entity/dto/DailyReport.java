package org.prime.internship.entity.dto;

import java.time.LocalDate;

public class DailyReport {
    private String companyName;
    private LocalDate date;
    private String city;
    private String employee;
    private Double turnover;

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

    @Override
    public String toString() {
        return "DailyReportDTO{" +
                "companyName='" + companyName + '\'' +
                ", date=" + date +
                ", city='" + city + '\'' +
                ", employee='" + employee + '\'' +
                ", turnover=" + turnover +
                '}';
    }
}
