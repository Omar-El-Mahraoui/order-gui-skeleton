package com.switchfully.vaadin.ordergui.webapp.views.validators;

import com.vaadin.data.Validator;

import java.util.regex.Pattern;

public class TextFieldNameValidator implements Validator {

    @Override
    public void validate(Object value) throws InvalidValueException {
        String s = (String) value;
        //https://www.moreofless.co.uk/check-string-contains-number-using-java/
        if (!isValid(s)) {
            throw new InvalidValueException("Name can only contain letters and must be between 1 and 10 characters" +
                    " long.");
        }
    }

    private boolean isValid(String s) {
        return !Pattern.compile("[0-9]").matcher(s).find()
                && s.length() > 0
                && s.length() < 10;
    }
}
