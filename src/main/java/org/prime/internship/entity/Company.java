package org.prime.internship.entity;

import java.time.LocalDate;

public class Company {
    private int company_id;
    private String name;
    private LocalDate lastDocumentDate;

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLastDocumentDate() {
        return lastDocumentDate;
    }

    public void setLastDocumentDate(LocalDate lastDocumentDate) {
        this.lastDocumentDate = lastDocumentDate;
    }
}
