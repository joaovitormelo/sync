package com.rubeusufv.sync.Exceptions;

public class SingletonViolationException extends RuntimeException {
    public SingletonViolationException() {

        super("Não é permitido criar nova instância para a classe singleton!");
    }
}
