package com.rubeusufv.sync.Core.Exceptions;

public class IncorrectPasswordException extends RuntimeException {
    private String userLogin;
    public IncorrectPasswordException(String userLogin) {
      super("Senha incorreta para usuário: " + userLogin);
    }
}
