package com.rubeusufv.sync.Core.Exceptions;

public class GoogleException extends ConnectionException {
    public GoogleException(String message, String details) {
        super("Erro na conexão com a Google:" + message, details);
    }
}
