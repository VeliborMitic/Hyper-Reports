package org.prime.internship.cli.validator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class MonthValidator implements IParameterValidator {
    @Override
    public void validate(String s, String s1){
        int month = Integer.parseInt(s1);
        if (month < 1 || month > 12) {
            throw new ParameterException("Parameter " + s + " must be between 1 - 12. (found " + s1 + ")");
        }
    }
}
