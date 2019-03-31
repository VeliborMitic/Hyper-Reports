package org.prime.internship.cli.validator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class TopBottomValidator implements IParameterValidator {
    @Override
    public void validate(String s, String s1) throws ParameterException {
        int value = Integer.parseInt(s1);
        if (value < 1 || value > 100) {
            throw new ParameterException("Parameter " + s + " must be between 1 - 100. (found " + s1 + ")");
        }
    }
}
