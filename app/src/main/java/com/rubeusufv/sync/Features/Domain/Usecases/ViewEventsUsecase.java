package com.rubeusufv.sync.Features.Domain.Usecases;

import com.rubeusufv.sync.Core.Session.SessionManager;
import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Features.Data.EventsData.EventsDataContract;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;

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
        ArrayList<EventModel> rubeusEventModels = rubeusData.viewEvents(currentUser, year, month);
        ArrayList<EventModel> googleEventModels = googleData.viewEvents(currentUser, year, month);
        ArrayList<EventModel> localEventModels = eventsData.viewEvents(currentUser, year, month);

        // Sincroniza eventos da Rubeus com o banco de dadps
        updateLocalEventsFromOutsideEvents(currentUser, localEventModels, rubeusEventModels);

        // Sincroniza eventos da Google com o banco de dados
        updateLocalEventsFromOutsideEvents(currentUser, localEventModels, googleEventModels);

        return localEventModels;
    }

    private void updateLocalEventsFromOutsideEvents(
        UserModel currentUser, ArrayList<EventModel> localEventModels,
        ArrayList<EventModel> outsideEventModels
    ) {
        for (EventModel outsideEventModel : outsideEventModels) {
            if (!localEventModels.contains(outsideEventModel)) {
                EventModel newEventModel = eventsData.createNewEvent(
                        currentUser, outsideEventModel
                );
                localEventModels.add(newEventModel);
            }
        }
    }
}
