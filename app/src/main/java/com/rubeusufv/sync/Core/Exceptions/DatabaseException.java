package com.rubeusufv.sync.Core.Exceptions;

public class DatabaseException extends ConnectionException {
    public DatabaseException(String message, String details) {
        super("Erro na conexão com o banco:" + message, details);
    }
}
