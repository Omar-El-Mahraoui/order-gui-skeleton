package com.switchfully.vaadin.ordergui.webapp.views.validators;

//http://www.vogella.com/tutorials/JavaRegularExpressions/article.html

import com.vaadin.data.Validator;

import java.util.regex.Pattern;

public class TextFieldAMountOfStockValidator implements Validator {

    @Override
    public void validate(Object value) throws InvalidValueException {
        Integer i;
        try {
            i = Integer.parseInt(String.valueOf(value));
            if (!isValid(i)) {
                throw new InvalidValueException("Price must be a number greater than 0.");
            }
        } catch (NumberFormatException e) {
            throw new InvalidValueException("Price must be a number greater than 0.");
        }
    }

    private boolean isValid(Integer i) {
        return !Pattern.compile("[a-zA-Z]").matcher(String.valueOf(i)).find()
                && i > 0;
    }

}
