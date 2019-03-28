package org.prime.internship.service;

import org.prime.internship.entity.Company;
import org.prime.internship.repository.CompanyRepository;

import java.time.LocalDate;

class CompanyService {
    private final CompanyRepository companyRepository;

    CompanyService() {
        this.companyRepository = new CompanyRepository();
    }

    Company processCompanyToDB(String name, String date) {
        Company company;
        //Check if company exists in DB, if not, use INSERT repository method
        if (companyRepository.getOneByName(name) == null) {
            company = new Company();
            company.setName(name);
            company.setLastDocumentDate(LocalDate.parse(date));
            company.setCompanyId(companyRepository.insert(company).getCompanyId());
        } else {
            // If company already exists in DB, use UPDATE repository method
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
