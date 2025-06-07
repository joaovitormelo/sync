package com.rubeusufv.sync.Core;

import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Core.Session.SessionManagerMock;
import com.rubeusufv.sync.Features.Data.AuthData.AuthDataContract;
import com.rubeusufv.sync.Features.Data.AuthData.AuthDataMock;
import com.rubeusufv.sync.Features.Data.EventsData.EventsDataContract;
import com.rubeusufv.sync.Features.Data.EventsData.DatabaseEventsDataMock;
import com.rubeusufv.sync.Features.Data.EventsData.GoogleEventsDataMock;
import com.rubeusufv.sync.Features.Data.EventsData.RubeusEventsDataMock;
import com.rubeusufv.sync.Features.Domain.Usecases.Authentication.DoLoginUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.Authentication.EnterWithoutLoginUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.EditEventUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.ExcludeEventUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.ImportEventListToRepositoryUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.ImportSingleEventToRepositoryUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.RegisterNewEventUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.ViewEventsUsecase;
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
    private ExcludeEventUsecase excludeEventUsecase;
    private ImportSingleEventToRepositoryUsecase importSingleEventToRepositoryUsecase;
    private ImportEventListToRepositoryUsecase importEventListToRepositoryUsecase;
    private EnterWithoutLoginUsecase enterWithoutLoginUsecase;

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
        viewEventsUsecase = new ViewEventsUsecase(
            rubeusEventsData, googleEventsData, databaseEventsData, sessionManager
        );
        doLoginUsecase = new DoLoginUsecase(authData, criptography, sessionManager);
        registerNewEventUsecase = new RegisterNewEventUsecase(
                rubeusEventsData, googleEventsData, databaseEventsData, sessionManager
        );
        editEventUsecase = new EditEventUsecase(
                rubeusEventsData, googleEventsData, databaseEventsData, sessionManager
        );
        excludeEventUsecase = new ExcludeEventUsecase(
                rubeusEventsData, googleEventsData, databaseEventsData, sessionManager
        );
        importSingleEventToRepositoryUsecase = new ImportSingleEventToRepositoryUsecase(
            googleEventsData, rubeusEventsData, sessionManager
        );
        importEventListToRepositoryUsecase = new ImportEventListToRepositoryUsecase(
            googleEventsData, rubeusEventsData, sessionManager
        );
        enterWithoutLoginUsecase = new EnterWithoutLoginUsecase(
            sessionManager
        );
    }

    public ViewEventsUsecase getEventUsecases() {
        return this.viewEventsUsecase;
    }

    public DoLoginUsecase getDoLoginUsecase() { return this.doLoginUsecase; }

    public RegisterNewEventUsecase getRegisterNewEventUsecase() { return this.registerNewEventUsecase; }
    public EditEventUsecase getEditEventUsecase() { return this.editEventUsecase; }
    public ExcludeEventUsecase getExcludeEventUsecase() {
        return this.excludeEventUsecase;
    }
    public ImportSingleEventToRepositoryUsecase getImportSingleEventToRepositoryUsecase() {
        return this.importSingleEventToRepositoryUsecase;
    }
    public ImportEventListToRepositoryUsecase getImportEventListToRepositoryUsecase() {
        return this.importEventListToRepositoryUsecase;
    }
    public EnterWithoutLoginUsecase getEnterWithoutLoginUsecase() {return this.enterWithoutLoginUsecase;}
}
