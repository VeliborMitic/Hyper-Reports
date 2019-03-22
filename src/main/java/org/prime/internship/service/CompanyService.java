package org.prime.internship.service;

import org.prime.internship.entity.Company;
import org.prime.internship.repository.CompanyRepository;

import java.time.LocalDate;

class CompanyService {
    private CompanyRepository companyRepository;

    CompanyService() {
        this.companyRepository = new CompanyRepository();
    }

    Company processCompanyToDB(String name, String date) {
        Company company;
        if (companyRepository.getOneByName(name) == null) {
            company = new Company();
            company.setName(name);
            company.setLastDocumentDate(LocalDate.parse(date));
            company.setCompanyId(companyRepository.insert(company).getCompanyId());
        } else {
            company = companyRepository.getOneByName(name);
            company.setLastDocumentDate(LocalDate.parse(date));
            companyRepository.update(company);
        }
        return company;
    }

    Company getOneByName(String name) {
        return companyRepository.getOneByName(name);
    }
}
