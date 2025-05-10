package com.rubeusufv.sync.Core;

/*
Classe responsável por criar as instâncias de todas as classes de ação do sistema
e injetar as dependências necessárias
 */
public final class Injector {
    private static Injector instance;

    private Injector() {
        initialize();
    }

    public static Injector getInstance() {
        if (instance != null) return instance;
        return new Injector();
    }

    private void initialize() {

    }
}
