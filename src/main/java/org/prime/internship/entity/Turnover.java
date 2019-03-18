package org.prime.internship.entity;

import java.time.LocalDate;


public class Turnover {
    private int turnover_id;
    private int employee_id;
    private LocalDate date;
    private double turnoverValue;

    public int getTurnover_id() {
        return turnover_id;
    }

    public void setTurnover_id(int turnover_id) {
        this.turnover_id = turnover_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTurnoverValue() {
        return turnoverValue;
    }

    public void setTurnoverValue(double turnoverValue) {
        this.turnoverValue = turnoverValue;
    }
}
