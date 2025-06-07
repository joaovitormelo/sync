package com.rubeusufv.sync.Features.Domain.Usecases;

import com.rubeusufv.sync.Core.Exceptions.DatabaseException;
import com.rubeusufv.sync.Core.Exceptions.GoogleException;
import com.rubeusufv.sync.Core.Exceptions.RubeusException;
import com.rubeusufv.sync.Core.Exceptions.UsecaseException;
import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Features.Data.EventsData.EventsDataContract;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;
import com.rubeusufv.sync.Features.Domain.Utils.DevTools;

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
        updateInGoogle(event, currentUser);
        updateInRubeus(event, currentUser);
        updateInDatabase(event, currentUser);
    }

    private void updateInGoogle(EventModel event, UserModel currentUser) {
        if (!event.isGoogleImported()) return;
        if (event.getGoogleId() == 0) {
            throw new UsecaseException("O evento deve possuir um ID da Google!");
        }
        try {
            googleData.updateEvent(currentUser, event);
        } catch(Exception error) {
            throw new GoogleException(
                    "Não foi possível atualizar evento!", DevTools.getDetailsFromError(error)
            );
        }
    }

    private void updateInRubeus(EventModel event, UserModel currentUser) {
        if (!event.isRubeusImported()) return;
        if (event.getRubeusId() == 0) {
            throw new UsecaseException("O evento deve possuir um ID da Rubeus!");
        }
        try {
            rubeusData.updateEvent(currentUser, event);
        } catch(Exception error) {
            throw new RubeusException(
                    "Não foi possível atualizar evento!", DevTools.getDetailsFromError(error)
            );
        }
    }

    private void updateInDatabase(EventModel event, UserModel currentUser) {
        try {
            eventsData.updateEvent(currentUser, event);
        } catch(Exception error) {
            throw new DatabaseException(
                    "Não foi possível atualizar evento!", DevTools.getDetailsFromError(error)
            );
        }
    }
}
