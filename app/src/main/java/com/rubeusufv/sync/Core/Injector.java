package com.rubeusufv.sync.Core;

import com.rubeusufv.sync.Exceptions.SingletonViolationException;
import com.rubeusufv.sync.Features.Data.RubeusData;
import com.rubeusufv.sync.Features.Domain.Repositories.EventRepository;
import com.rubeusufv.sync.Features.Domain.Usecases.EventUsecases;

/*
Classe responsável por criar as instâncias de todas as classes de ação do sistema
e injetar as dependências necessárias
 */
public final class Injector {
    private static Injector instance;
    private EventRepository eventRepository;
    private EventUsecases eventUsecases;
    private RubeusData rubeusData;

    private Injector() {
        initialize();
    }

    public static Injector getInstance() {
        if (instance != null) return instance;
        return new Injector();
    }

    private void initialize() {
        rubeusData = RubeusData.createInstance();
        eventRepository = EventRepository.createInstance(rubeusData);
        eventUsecases = EventUsecases.createInstance(eventRepository);
    }
}
