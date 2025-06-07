package com.rubeusufv.sync.Features.Domain.Usecases.Events;

import com.rubeusufv.sync.Core.Exceptions.DatabaseException;
import com.rubeusufv.sync.Core.Exceptions.GoogleException;
import com.rubeusufv.sync.Core.Exceptions.RubeusException;
import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Features.Data.EventsData.EventsDataContract;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;
import com.rubeusufv.sync.Features.Domain.Utils.DevTools;

import java.util.ArrayList;

public class ViewEventsUsecase {
    private EventsDataContract rubeusData;
    private EventsDataContract googleData;
    private EventsDataContract eventsData;
    private SessionManagerContract sessionManager;

    public ViewEventsUsecase(
        EventsDataContract rubeusData, EventsDataContract googleData,
        EventsDataContract eventsData, SessionManagerContract sessionManager
    ) {
        this.rubeusData = rubeusData;
        this.googleData = googleData;
        this.eventsData = eventsData;
        this.sessionManager = sessionManager;
    }

    public ArrayList<EventModel> viewEvents(int year, int month) {
        UserModel currentUser = sessionManager.getSessionUser();
        ArrayList<EventModel> rubeusEvents, googleEvents, localEvents;
        try {
            rubeusEvents = rubeusData.viewEvents(currentUser, year, month);
        } catch(Exception error) {
            throw new RubeusException(
                    "Não foi possível buscar os eventos!", DevTools.getDetailsFromError(error)
            );
        }
        try {
            googleEvents = googleData.viewEvents(currentUser, year, month);
        } catch(Exception error) {
            throw new GoogleException(
                    "Não foi possível buscar os eventos!", DevTools.getDetailsFromError(error)
            );
        }
        try {
            localEvents = eventsData.viewEvents(currentUser, year, month);
        } catch(Exception error) {
            throw new DatabaseException(
                    "Não foi possível buscar os eventos!", DevTools.getDetailsFromError(error)
            );
        }

        // Sincroniza eventos da Rubeus com o banco de dados
        updateLocalEventsFromOutsideEvents(
            currentUser, localEvents, rubeusEvents, false
        );

        // Sincroniza eventos da Google com o banco de dados
        updateLocalEventsFromOutsideEvents(
            currentUser, localEvents, googleEvents, true
        );

        return localEvents;
    }

    private void updateLocalEventsFromOutsideEvents(
        UserModel currentUser, ArrayList<EventModel> localEvents,
        ArrayList<EventModel> outsideEvents, boolean isGoogle
    ) {
        for (EventModel outsideEvent : outsideEvents) {
            if (!hasEvent(localEvents, outsideEvent, isGoogle)) {
                EventModel newEventModel;
                try {
                    newEventModel = eventsData.createNewEvent(
                            currentUser, outsideEvent
                    );
                } catch(Exception error) {
                    throw new DatabaseException(
                            "Não foi possível criar evento!", DevTools.getDetailsFromError(error)
                    );
                }
                localEvents.add(newEventModel);
            }
        }
    }

    private boolean hasEvent(
        ArrayList<EventModel> eventList, EventModel event, boolean isGoogle
    ) {
        for (EventModel e : eventList) {
            if (isGoogle) {
                if (e.getGoogleId() == event.getGoogleId()) return true;
            } else {
                if (e.getRubeusId() == event.getRubeusId()) return true;
            }
        }
        return false;
    }
}
