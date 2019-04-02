package org.prime.internship.cli.validator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.time.LocalDate;

public class YearValidator implements IParameterValidator {
    @Override
    public void validate(String s, String s1) throws ParameterException {
        int year = Integer.parseInt(s1);
        if (year < 0) {
            throw new ParameterException("Parameter " + s + " should be positive. (found " + s1 + ")");
        }
        if (year > LocalDate.now().getYear()) {
            throw new ParameterException("Parameter " + s + " can't be in future. (found " + s1 + ")");
        }
    }
}
