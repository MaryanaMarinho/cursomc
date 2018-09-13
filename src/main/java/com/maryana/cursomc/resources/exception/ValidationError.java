package com.maryana.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{

    private static final long serialVersionUID = 1L;

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Integer status, String msg, Long timeStamp) {
        super(status, msg, timeStamp);
    }

    //é no nome do get que aparece la no json, ex: getErrors, vai aparecer errors la
    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String message) {

        errors.add(new FieldMessage(fieldName, message));
    }
}
