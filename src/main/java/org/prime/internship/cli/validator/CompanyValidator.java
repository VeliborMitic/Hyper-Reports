package org.prime.internship.cli.validator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import org.prime.internship.entity.Company;
import org.prime.internship.service.CompanyService;

import java.util.Optional;

public class CompanyValidator   implements IParameterValidator {
    private CompanyService companyService = new CompanyService();

    @Override
    public void validate(String s, String s1) {
        Optional<Company> company = Optional.ofNullable(companyService.getOneByName(s1.toLowerCase()));
        if (!company.isPresent()) {
            throw new ParameterException("Company " + s1 + " does not exist in databasse!");
        }
    }
}
