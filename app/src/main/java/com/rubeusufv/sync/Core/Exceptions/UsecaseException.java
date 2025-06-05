package com.rubeusufv.sync.Core.Exceptions;

public class UsecaseException extends RuntimeException {
    public UsecaseException(String message) {
      super("Erro nas regras de negócio: " + message);
    }
}
