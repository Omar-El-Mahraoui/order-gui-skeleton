package com.switchfully.vaadin.ordergui.webapp.views.validators;

import com.vaadin.data.Validator;

import java.util.regex.Pattern;

//http://www.vogella.com/tutorials/JavaRegularExpressions/article.html

public class TextFieldPriceValidator implements Validator {

    @Override
    public void validate(Object value) throws InvalidValueException {
        Float f;
        try {
            f = Float.parseFloat(String.valueOf(value));
            if (!isValid(f)) {
                throw new InvalidValueException("Price must be a number greater than 0.");
            }
        } catch (NumberFormatException e) {
            throw new InvalidValueException("Price must be a number greater than 0.");
        }
    }

    private boolean isValid(Float f) {
        return !Pattern.compile("[a-zA-Z]").matcher(String.valueOf(f)).find()
                && f > 0;
    }

}
