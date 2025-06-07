package com.rubeusufv.sync.Core.Exceptions;

public class ConnectionException extends RuntimeException {
    private String details;
    public ConnectionException(String message, String details) {
        super(message);
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
}
