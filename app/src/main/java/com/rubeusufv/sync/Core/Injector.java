package com.rubeusufv.sync.Core;

import com.rubeusufv.sync.Features.Data.EventsData;
import com.rubeusufv.sync.Features.Data.GoogleData;
import com.rubeusufv.sync.Features.Data.RubeusData;
import com.rubeusufv.sync.Features.Domain.Usecases.EventUsecases;

/*
Classe responsável por criar as instâncias de todas as classes de ação do sistema
e injetar as dependências necessárias
 */
public final class Injector {
    private static Injector instance;
    private EventUsecases eventUsecases;
    private EventsData eventsData;
    private GoogleData googleData;
    private RubeusData rubeusData;

    private Injector() {
        initialize();
    }

    public static Injector getInstance() {
        if (instance != null) return instance;
        return instance = new Injector();
    }

    private void initialize() {
        eventsData = new EventsData();
        googleData = new GoogleData();
        rubeusData = new RubeusData();
        eventUsecases = new EventUsecases(rubeusData, googleData, eventsData);
    }

    public EventUsecases getEventUsecases() {
        return this.eventUsecases;
    }
}
