package org.prime.internship.entity;

import java.io.Serializable;
import java.time.LocalDate;


public class Turnover  implements Serializable {
    private static final long serialVersionUID = 6977947445823075897L;
    private int turnoverId;
    private int employeeId;
    private LocalDate date;
    private double turnoverValue;

    public int getTurnoverId() {
        return turnoverId;
    }

    public void setTurnoverId(int turnoverId) {
        this.turnoverId = turnoverId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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
