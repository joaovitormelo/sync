package com.rubeusufv.sync.Features.Domain.Usecases.Events;

import android.util.Log;

import com.rubeusufv.sync.Core.Exceptions.DatabaseException;
import com.rubeusufv.sync.Core.Exceptions.GoogleException;
import com.rubeusufv.sync.Core.Exceptions.RubeusException;
import com.rubeusufv.sync.Core.Exceptions.UsecaseException;
import com.rubeusufv.sync.Core.Exceptions.ValidationException;
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
    public void registerNewEvent(EventModel event) throws DatabaseException,
            RubeusException, GoogleException, ValidationException {
        validateFields(event);
        UserModel currentUser = sessionManager.getSessionUser();
        createInGoogle(event, currentUser);
        createInRubeus(event, currentUser);
        createInDatabase(event, currentUser);
    }

    private void validateFields(EventModel event) {
        if (event.getTitle().isEmpty()) {
            throw new ValidationException("title", "O título não pode ser vazio!");
        }
        if (event.getDescription().isEmpty()) {
            throw new ValidationException("description", "A descrição não pode ser vazia!");
        }
        if (event.getDate() == null) {
            throw new ValidationException("date", "A data não pode ser vazia!");
        }
        if (event.getStartHour().isEmpty()) {
            throw new ValidationException("startHour", "A hora de início não pode ser vazia!");
        }
        if (event.getEndHour().isEmpty()) {
            throw new ValidationException("endHour", "A hora de fim não pode ser vazia!");
        }
        if (event.getCategory().isEmpty()) {
            throw new ValidationException("category", "A categoria não pode ser vazia!");
        }
        if (!event.isGoogleImported() && !event.isRubeusImported()) {
            throw new ValidationException("imported", "O evento deve ser criado em pelo menos um dos repositórios!");
        }
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
