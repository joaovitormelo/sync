package com.rubeusufv.sync.Features.Domain.Usecases;

import com.rubeusufv.sync.Core.Exceptions.UsecaseException;
import com.rubeusufv.sync.Core.Session.SessionManager;
import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Features.Data.EventsData.EventsDataContract;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;

public class ExcludeEventUsecase {
    private EventsDataContract rubeusData;
    private EventsDataContract googleData;
    private EventsDataContract eventsData;
    private SessionManagerContract sessionManager;

    public ExcludeEventUsecase(
            EventsDataContract rubeusData, EventsDataContract googleData,
            EventsDataContract eventsData, SessionManagerContract sessionManager
    ) {
        this.rubeusData = rubeusData;
        this.googleData = googleData;
        this.eventsData = eventsData;
        this.sessionManager = sessionManager;
    }

    public void excludeEvent (
        EventModel event, boolean excludeFromRubeus, boolean excludeFromGoogle
    ) {
        UserModel currentUser = sessionManager.getSessionUser();
        if (excludeFromGoogle) {
            if (event.getGoogleId() == 0) {
                throw new UsecaseException("O evento deve possuir um ID da Google!");
            }
            if (!event.isGoogleImported()) {
                throw new UsecaseException("Não é possível excluir da Google um evento que não está importado na Google!");
            }
            googleData.removeEvent(currentUser, event);
            event.setGoogleImported(false);
        }
        if (excludeFromRubeus) {
            if (event.getRubeusId() == 0) {
                throw new UsecaseException("O evento deve possuir um ID da Rubeus!");
            }
            if (!event.isRubeusImported()) {
                throw new UsecaseException("Não é possível excluir da Rubeus um evento que não está importado na Rubeus!");
            }
            rubeusData.removeEvent(currentUser, event);
            event.setRubeusImported(false);
        }
        if (!event.isGoogleImported() && !event.isRubeusImported()) {
            if (event.getId() == 0) {
                throw new UsecaseException("O evento deve possuir um ID no banco de dados!");
            }
            eventsData.removeEvent(currentUser, event);
        }
    }
}
