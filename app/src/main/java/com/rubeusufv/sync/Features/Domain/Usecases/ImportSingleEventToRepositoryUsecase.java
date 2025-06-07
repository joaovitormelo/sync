package com.rubeusufv.sync.Features.Domain.Usecases;

import com.rubeusufv.sync.Core.Exceptions.GoogleException;
import com.rubeusufv.sync.Core.Exceptions.RubeusException;
import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Features.Data.EventsData.EventsDataContract;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;
import com.rubeusufv.sync.Features.Domain.Utils.DevTools;

public class ImportSingleEventToRepositoryUsecase {
    EventsDataContract googleData;
    EventsDataContract rubeusData;
    SessionManagerContract sessionManager;

    public ImportSingleEventToRepositoryUsecase(
        EventsDataContract googleData, EventsDataContract rubeusData,
        SessionManagerContract sessionManager
    ) {
        this.googleData = googleData;
        this.rubeusData = rubeusData;
        this.sessionManager = sessionManager;
    }

    public void importSingleEventToRepository(
        EventModel event, boolean importToGoogle, boolean importToRubeus
    ) {
        UserModel currentUser = sessionManager.getSessionUser();
        if (importToGoogle && !event.isGoogleImported()) {
            importToGoogle(event, currentUser);
        }
        if (importToRubeus && !event.isRubeusImported()) {
            importToRubeus(event, currentUser);
        }
    }

    private void importToGoogle(EventModel event, UserModel currentUser) {
        try {
            googleData.createNewEvent(currentUser, event);
        } catch(Exception error) {
            throw new GoogleException(
                    "Não foi possível importar evento!", DevTools.getDetailsFromError(error)
            );
        }
    }

    private void importToRubeus(EventModel event, UserModel currentUser) {
        try {
            rubeusData.createNewEvent(currentUser, event);
        } catch(Exception error) {
            throw new RubeusException(
                    "Não foi possível importar evento!", DevTools.getDetailsFromError(error)
            );
        }
    }
}
