package com.rubeusufv.sync.Features.Domain.Usecases;

import com.rubeusufv.sync.Core.Exceptions.UsecaseException;
import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Features.Data.EventsData.EventsDataContract;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;

public class EditEventUsecase {
    private EventsDataContract rubeusData;
    private EventsDataContract googleData;
    private EventsDataContract eventsData;
    private SessionManagerContract sessionManager;

    public EditEventUsecase(
            EventsDataContract rubeusData, EventsDataContract googleData,
            EventsDataContract eventsData, SessionManagerContract sessionManager
    ) {
        this.rubeusData = rubeusData;
        this.googleData = googleData;
        this.eventsData = eventsData;
        this.sessionManager = sessionManager;
    }

    public void editEvent(EventModel event) {
        if (event.getId() == 0) {
            throw new UsecaseException("O evento deve possuir um ID no banco de dados!");
        }
        if (!event.isGoogleImported() && !event.isRubeusImported()) {
            throw new UsecaseException("O evento deve permanecer criado em pelo menos um dos repositórios de dados!");
        }
        UserModel currentUser = sessionManager.getSessionUser();
        if (event.isGoogleImported()) {
            if (event.getGoogleId() == 0) {
                throw new UsecaseException("O evento deve possuir um ID da Google!");
            }
            googleData.updateEvent(currentUser, event);
        }
        if (event.isRubeusImported()) {
            if (event.getRubeusId() == 0) {
                throw new UsecaseException("O evento deve possuir um ID da Rubeus!");
            }
            rubeusData.updateEvent(currentUser, event);
        }
        eventsData.updateEvent(currentUser, event);
    }
}
