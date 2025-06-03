package com.rubeusufv.sync.Core;

import com.rubeusufv.sync.Features.Data.EventsDataMock;
import com.rubeusufv.sync.Features.Data.GoogleDataMock;
import com.rubeusufv.sync.Features.Data.RubeusDataMock;
import com.rubeusufv.sync.Features.Domain.Usecases.ViewEventsUsecase;

/*
Classe responsável por criar as instâncias de todas as classes de ação do sistema
e injetar as dependências necessárias
 */
public final class Injector {
    private static Injector instance;
    private ViewEventsUsecase viewEventsUsecase;
    private EventsDataMock eventsDataMock;
    private GoogleDataMock googleDataMock;
    private RubeusDataMock rubeusDataMock;

    private Injector() {
        initialize();
    }

    public static Injector getInstance() {
        if (instance != null) return instance;
        return instance = new Injector();
    }

    private void initialize() {
        eventsDataMock = new EventsDataMock();
        googleDataMock = new GoogleDataMock();
        rubeusDataMock = new RubeusDataMock();
        viewEventsUsecase = new ViewEventsUsecase(rubeusDataMock, googleDataMock, eventsDataMock);
    }

    public ViewEventsUsecase getEventUsecases() {
        return this.viewEventsUsecase;
    }
}
