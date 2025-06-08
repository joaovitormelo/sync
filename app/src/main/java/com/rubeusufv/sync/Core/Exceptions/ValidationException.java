package com.rubeusufv.sync.Core.Exceptions;

public class ValidationException extends RuntimeException {
    private String field;
    public ValidationException(String field, String message) {

        super(message);
        this.field = field;
    }
    public String getField() {
        return this.field;
    }
}
