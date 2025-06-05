package com.rubeusufv.sync.Core;

import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Core.Session.SessionManagerMock;
import com.rubeusufv.sync.Features.Data.EventsDataContract;
import com.rubeusufv.sync.Features.Data.EventsDataMock;
import com.rubeusufv.sync.Features.Data.GoogleDataContract;
import com.rubeusufv.sync.Features.Data.GoogleDataMock;
import com.rubeusufv.sync.Features.Data.RubeusDataContract;
import com.rubeusufv.sync.Features.Data.RubeusDataMock;
import com.rubeusufv.sync.Features.Domain.Usecases.DoLoginUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.EditEventUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.RegisterNewEventUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.ViewEventsUsecase;
import com.rubeusufv.sync.Tools.Criptography.CriptographyContract;
import com.rubeusufv.sync.Tools.Criptography.CriptographyMock;

/*
Classe responsável por criar as instâncias de todas as classes de ação do sistema
e injetar as dependências necessárias
 */
public final class Injector {
    private static Injector instance;
    private ViewEventsUsecase viewEventsUsecase;
    private DoLoginUsecase doLoginUsecase;
    private SessionManagerContract sessionManager;
    private CriptographyContract criptography;
    private EventsDataContract eventsData;
    private GoogleDataContract googleData;
    private RubeusDataContract rubeusData;
    private RegisterNewEventUsecase registerNewEventUsecase;
    private EditEventUsecase editEventUsecase;

    private Injector() {
        initialize();
    }

    public static Injector getInstance() {
        if (instance != null) return instance;
        return instance = new Injector();
    }

    private void initialize() {
        eventsData = new EventsDataMock();
        googleData = new GoogleDataMock();
        rubeusData = new RubeusDataMock();
        criptography = new CriptographyMock();
        sessionManager = new SessionManagerMock();
        viewEventsUsecase = new ViewEventsUsecase(rubeusData, googleData, eventsData);
        doLoginUsecase = new DoLoginUsecase(rubeusData, criptography, sessionManager);
        registerNewEventUsecase = new RegisterNewEventUsecase(rubeusData, googleData, eventsData);
        editEventUsecase = new EditEventUsecase(rubeusData, googleData, eventsData);
    }

    public ViewEventsUsecase getEventUsecases() {
        return this.viewEventsUsecase;
    }

    public DoLoginUsecase getDoLoginUsecase() { return this.doLoginUsecase; }

    public RegisterNewEventUsecase getRegisterNewEventUsecase() { return this.registerNewEventUsecase; }
    public EditEventUsecase getEditEventUsecase() { return this.editEventUsecase; }
}
