package org.prime.internship.entity.dto;

import java.time.LocalDate;

public class ReportDTO {
    private String companyName;
    private LocalDate date;
    private String employee;
    private String city;
    private String department;
    private double turnover;
    private int month;
    private int quarter;
    private int year;
    private int rowNumber;

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

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    public String getEmployee() {return employee;}

    public void setEmployee(String employee) {this.employee = employee;}

    public String getCity() {return city;}

    public void setCity(String city) {this.city = city;}

    public String getDepartment() {return department;}

    public void setDepartment(String department) {this.department = department;}

    public int getMonth() {return month;}

    public void setMonth(int month) {this.month = month;}

    public int getQuarter() {return quarter;}

    public void setQuarter(int quarter) {this.quarter = quarter;}

    public int getYear() {return year;}

    public void setYear(int year) {this.year = year;}

    public int getRowNumber() {return rowNumber;}

    public void setRowNumber(int rowNumber) {this.rowNumber = rowNumber;}
}
