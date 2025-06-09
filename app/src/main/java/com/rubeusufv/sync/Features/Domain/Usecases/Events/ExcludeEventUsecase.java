package com.rubeusufv.sync.Features.Domain.Usecases.Events;

import com.rubeusufv.sync.Core.Exceptions.DatabaseException;
import com.rubeusufv.sync.Core.Exceptions.GoogleException;
import com.rubeusufv.sync.Core.Exceptions.RubeusException;
import com.rubeusufv.sync.Core.Exceptions.UsecaseException;
import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Features.Data.EventsData.EventsDataContract;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;
import com.rubeusufv.sync.Features.Domain.Utils.DevTools;

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
        excludeFromGoogle(excludeFromGoogle, event, currentUser);
        excludeFromRubeus(excludeFromRubeus, event, currentUser);
        excludeFromDatabase(event, currentUser);
    }

    private void excludeFromGoogle(boolean excludeFromGoogle, EventModel event, UserModel currentUser) {
        if (!excludeFromGoogle) return;
        if (!event.isGoogleImported()) {
            return;
        }
        if (event.getGoogleId() == 0) {
            throw new UsecaseException("O evento deve possuir um ID da Google!");
        }
        try {
            googleData.removeEvent(currentUser, event);
        } catch(Exception error) {
            throw new GoogleException(
                    "Não foi possível excluir evento!", DevTools.getDetailsFromError(error)
            );
        }
        event.setGoogleImported(false);
    }

    private void excludeFromRubeus(boolean excludeFromRubeus, EventModel event, UserModel currentUser) {
        if (!excludeFromRubeus) return;
        if (!event.isRubeusImported()) {
            return;
        }
        if (event.getRubeusId() == 0) {
            throw new UsecaseException("O evento deve possuir um ID da Rubeus!");
        }
        try {
            rubeusData.removeEvent(currentUser, event);
        } catch(Exception error) {
            throw new RubeusException(
                    "Não foi possível excluir evento!", DevTools.getDetailsFromError(error)
            );
        }
        event.setRubeusImported(false);
    }

    private void excludeFromDatabase(EventModel event, UserModel currentUser) {
        if (event.isGoogleImported() || event.isRubeusImported()) return;
        if (event.getId() == 0) {
            throw new UsecaseException("O evento deve possuir um ID no banco de dados!");
        }
        try {
            eventsData.removeEvent(currentUser, event);
        } catch(Exception error) {
            throw new DatabaseException(
                    "Não foi possível excluir evento!", DevTools.getDetailsFromError(error)
            );
        }
    }
}
