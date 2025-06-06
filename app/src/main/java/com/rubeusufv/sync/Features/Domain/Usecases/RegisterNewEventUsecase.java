package com.rubeusufv.sync.Features.Domain.Usecases;

import com.rubeusufv.sync.Core.Exceptions.UsecaseException;
import com.rubeusufv.sync.Core.Session.SessionManager;
import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Features.Data.EventsData.EventsDataContract;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;

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
        if (event.isGoogleImported()) {
            EventModel eventGoogle = googleData.createNewEvent(currentUser, event);
            updateEventWithGoogleFields(event, eventGoogle);
            event.setGoogleImported(true);
        }
        if (event.isRubeusImported()) {
            EventModel eventRubeus = rubeusData.createNewEvent(currentUser, event);
            updateEventWithRubeusFields(event, eventRubeus);
            event.setRubeusImported(true);
        }
        eventsData.createNewEvent(currentUser, event);
    }

    void updateEventWithGoogleFields(EventModel event, EventModel eventGoogle) {
        event.setGoogleId(eventGoogle.getGoogleId());
    }

    void updateEventWithRubeusFields(EventModel event, EventModel eventRubeus) {
        event.setRubeusId(eventRubeus.getRubeusId());
        event.setContactType(eventRubeus.getContactType());
    }
}
