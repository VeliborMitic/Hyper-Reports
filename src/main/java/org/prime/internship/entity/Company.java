package org.prime.internship.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Company implements Serializable {
    private static final long serialVersionUID = 3290684569571507828L;
    private int companyId;
    private String name;
    private LocalDate lastDocumentDate;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
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