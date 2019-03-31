package org.prime.internship.cli.validator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class QuarterValidator  implements IParameterValidator {
    @Override
    public void validate(String s, String s1) throws ParameterException {
        int quarter = Integer.parseInt(s1);
        if (quarter < 1 || quarter > 4) {
            throw new ParameterException("Parameter " + s + " must be between 1 - 4. (found " + s1 + ")");
        }
    }
}
