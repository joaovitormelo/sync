package com.rubeusufv.sync.Core.Exceptions;

public class RubeusException extends ConnectionException {
    public RubeusException(String message, String details) {
        super("Erro na conexão com a Rubeus:" + message, details);
    }
}
