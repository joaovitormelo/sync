package com.rubeusufv.sync.Features.Domain.Usecases;

import com.rubeusufv.sync.Core.Exceptions.DatabaseException;
import com.rubeusufv.sync.Core.Exceptions.GoogleException;
import com.rubeusufv.sync.Core.Exceptions.RubeusException;
import com.rubeusufv.sync.Core.Exceptions.UsecaseException;
import com.rubeusufv.sync.Core.Session.SessionManager;
import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Features.Data.EventsData.EventsDataContract;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;
import com.rubeusufv.sync.Features.Domain.Utils.DevTools;

public class RegisterNewEventUsecase {
    private EventsDataContract rubeusData;
    private EventsDataContract googleData;
    private EventsDataContract eventsData;
    private SessionManagerContract sessionManager;

    public RegisterNewEventUsecase(
            EventsDataContract rubeusData, EventsDataContract googleData,
            EventsDataContract eventsData, SessionManagerContract sessionManager
    ) {
        this.rubeusData = rubeusData;
        this.googleData = googleData;
        this.eventsData = eventsData;
        this.sessionManager = sessionManager;
    }
    public void registerNewEvent(EventModel event) {
        if (!event.isGoogleImported() && !event.isRubeusImported()) {
            throw new UsecaseException("O evento deve ser criado em pelo menos um dos repositórios de dados!");
        }
        UserModel currentUser = sessionManager.getSessionUser();
        createInGoogle(event, currentUser);
        createInRubeus(event, currentUser);
        createInDatabase(event, currentUser);
    }

    private void createInGoogle(EventModel event, UserModel currentUser) {
        if (!event.isGoogleImported()) return;
        EventModel eventGoogle;
        try {
            eventGoogle = googleData.createNewEvent(currentUser, event);
        } catch(Exception error) {
            throw new GoogleException(
                    "Não foi possível criar evento!", DevTools.getDetailsFromError(error)
            );
        }
        updateEventWithGoogleFields(event, eventGoogle);
        event.setGoogleImported(true);
    }

    private void createInRubeus(EventModel event, UserModel currentUser) {
        if (!event.isRubeusImported()) return;
        EventModel eventRubeus;
        try {
            eventRubeus = rubeusData.createNewEvent(currentUser, event);
        } catch(Exception error) {
            throw new RubeusException(
                    "Não foi possível criar evento!", DevTools.getDetailsFromError(error)
            );
        }
        updateEventWithRubeusFields(event, eventRubeus);
        event.setRubeusImported(true);
    }

    private void createInDatabase(EventModel event, UserModel currentUser) {
        try {
            eventsData.createNewEvent(currentUser, event);
        } catch(Exception error) {
            throw new DatabaseException(
                    "Não foi possível criar evento!", DevTools.getDetailsFromError(error)
            );
        }
    }

    void updateEventWithGoogleFields(EventModel event, EventModel eventGoogle) {
        event.setGoogleId(eventGoogle.getGoogleId());
    }

    void updateEventWithRubeusFields(EventModel event, EventModel eventRubeus) {
        event.setRubeusId(eventRubeus.getRubeusId());
        event.setContactType(eventRubeus.getContactType());
    }
}
