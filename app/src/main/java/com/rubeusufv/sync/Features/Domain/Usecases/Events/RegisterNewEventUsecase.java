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
import com.rubeusufv.sync.Features.Domain.Utils.ValidationHelper;

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
        Log.d("TESTE", "HORA DE INÍCIO: " + event.getStartHour());
        Log.d("TESTE", "HORA DE FIM: " + event.getEndHour());
        Log.d("TESTE", "CATEGORIA: " + event.getCategory());
        if (event.getStartHour().isEmpty()) {
            throw new ValidationException("startHour", "A hora de início não pode ser vazia!");
        }
        if (!ValidationHelper.validateHour(event.getStartHour())) {
            throw new ValidationException("startHour", "Informe uma hora de início válida!");
        }
        if (event.getEndHour().isEmpty()) {
            throw new ValidationException("endHour", "A hora de fim não pode ser vazia!");
        }
        if (!ValidationHelper.validateHour(event.getEndHour())) {
            throw new ValidationException("endHour", "Informe uma hora de fim válida!");
        }
        if (event.getCategory() != null && !checkValidValueForCategory(event.getCategory())) {
            throw new ValidationException("category", "Informe uma categoria válida!");
        }
        if (!event.isGoogleImported() && !event.isRubeusImported()) {
            throw new ValidationException("imported", "O evento deve ser criado em pelo menos um dos repositórios!");
        }
    }

    private boolean checkValidValueForCategory(String category) {
        return (
            category.equals(EventModel.CATEGORY.TEST)||
            category.equals(EventModel.CATEGORY.REUNION) ||
            category.equals(EventModel.CATEGORY.LEISURE) ||
            category.equals(EventModel.CATEGORY.WORK)
        );
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
