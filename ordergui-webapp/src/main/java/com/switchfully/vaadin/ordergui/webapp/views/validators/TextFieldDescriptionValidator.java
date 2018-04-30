package com.switchfully.vaadin.ordergui.webapp.views.validators;

import com.vaadin.data.Validator;

public class TextFieldDescriptionValidator implements Validator {

    @Override
    public void validate(Object value) throws InvalidValueException {
        String s = (String) value;
        //https://www.moreofless.co.uk/check-string-contains-number-using-java/
        if (!isValid(s)) {
            throw new InvalidValueException("Description must be between 5 and 50 characters long.");
        }
    }

    private boolean isValid(String s) {
        return s.length() >= 5
                && s.length() <= 50;
    }

}
