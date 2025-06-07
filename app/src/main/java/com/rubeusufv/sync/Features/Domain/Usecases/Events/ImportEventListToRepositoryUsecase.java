package com.rubeusufv.sync.Features.Domain.Usecases.Events;

import com.rubeusufv.sync.Core.Exceptions.GoogleException;
import com.rubeusufv.sync.Core.Exceptions.RubeusException;
import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Features.Data.EventsData.EventsDataContract;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;
import com.rubeusufv.sync.Features.Domain.Utils.DevTools;

import java.util.ArrayList;

public class ImportEventListToRepositoryUsecase {
    EventsDataContract googleData;
    EventsDataContract rubeusData;
    SessionManagerContract sessionManager;

    public ImportEventListToRepositoryUsecase(
            EventsDataContract googleData, EventsDataContract rubeusData,
            SessionManagerContract sessionManager
    ) {
        this.googleData = googleData;
        this.rubeusData = rubeusData;
        this.sessionManager = sessionManager;
    }

    public void importEventListToRepositoryUsecase(
            ArrayList<EventModel> eventList, boolean importToGoogle, boolean importToRubeus
    ) {
        UserModel currentUser = sessionManager.getSessionUser();
        for (EventModel event : eventList) {
            importSingleEvent(currentUser, event, importToGoogle, importToRubeus);
        }
    }

    private void importSingleEvent(
        UserModel currentUser, EventModel event, boolean importToGoogle, boolean importToRubeus
    ) {
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
                "Não foi possível importar evento \"" + event.getTitle() + "\"",
                DevTools.getDetailsFromError(error)
            );
        }
    }

    private void importToRubeus(EventModel event, UserModel currentUser) {
        try {
            rubeusData.createNewEvent(currentUser, event);
        } catch(Exception error) {
            throw new RubeusException(
                "Não foi possível importar evento \"" + event.getTitle() + "\"",
                DevTools.getDetailsFromError(error)
            );
        }
    }
}
