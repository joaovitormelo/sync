package com.rubeusufv.sync.Core;

import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Core.Session.SessionManagerMock;
import com.rubeusufv.sync.Features.Data.AuthData.AuthDataContract;
import com.rubeusufv.sync.Features.Data.AuthData.AuthDataMock;
import com.rubeusufv.sync.Features.Data.EventsData.EventsDataContract;
import com.rubeusufv.sync.Features.Data.EventsData.DatabaseEventsDataMock;
import com.rubeusufv.sync.Features.Data.EventsData.GoogleEventsDataMock;
import com.rubeusufv.sync.Features.Data.EventsData.RubeusEventsDataMock;
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
    private EventsDataContract databaseEventsData;
    private EventsDataContract googleEventsData;
    private EventsDataContract rubeusEventsData;
    private AuthDataContract authData;
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
        databaseEventsData = new DatabaseEventsDataMock();
        googleEventsData = new GoogleEventsDataMock();
        rubeusEventsData = new RubeusEventsDataMock();
        authData = new AuthDataMock();
        criptography = new CriptographyMock();
        sessionManager = new SessionManagerMock();
        viewEventsUsecase = new ViewEventsUsecase(rubeusEventsData, googleEventsData, databaseEventsData);
        doLoginUsecase = new DoLoginUsecase(authData, criptography, sessionManager);
        registerNewEventUsecase = new RegisterNewEventUsecase(rubeusEventsData, googleEventsData, databaseEventsData);
        editEventUsecase = new EditEventUsecase(rubeusEventsData, googleEventsData, databaseEventsData);
    }

    public ViewEventsUsecase getEventUsecases() {
        return this.viewEventsUsecase;
    }

    public DoLoginUsecase getDoLoginUsecase() { return this.doLoginUsecase; }

    public RegisterNewEventUsecase getRegisterNewEventUsecase() { return this.registerNewEventUsecase; }
    public EditEventUsecase getEditEventUsecase() { return this.editEventUsecase; }
}
