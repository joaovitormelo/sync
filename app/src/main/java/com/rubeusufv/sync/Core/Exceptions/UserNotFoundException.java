package com.rubeusufv.sync.Core.Exceptions;

public class UserNotFoundException extends RuntimeException {
    private String userLogin;
    public UserNotFoundException(String userLogin) {
        super("Usuário não encontrado: " + userLogin);
    }
}
