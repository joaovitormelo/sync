package com.rubeusufv.sync.Features.Domain.Usecases;

import com.rubeusufv.sync.Features.Data.EventsDataContract;
import com.rubeusufv.sync.Features.Data.GoogleDataContract;
import com.rubeusufv.sync.Features.Data.RubeusDataContract;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;

import java.util.ArrayList;

public class ViewEventsUsecase {
    private RubeusDataContract rubeusData;
    private GoogleDataContract googleData;
    private EventsDataContract eventsData;

    public ViewEventsUsecase(
        RubeusDataContract rubeusData, GoogleDataContract googleData,
        EventsDataContract eventsData
    ) {
        this.rubeusData = rubeusData;
        this.googleData = googleData;
        this.eventsData = eventsData;
    }

    public ArrayList<EventModel> viewEvents(int year, int month) {
        ArrayList<EventModel> rubeusEventModels = rubeusData.viewEvents(year, month);
        ArrayList<EventModel> googleEventModels = googleData.viewEvents(year, month);
        ArrayList<EventModel> localEventModels = eventsData.viewEvents(year, month);

        // Sincroniza eventos da Rubeus com o banco de dadps
        updateLocalEventsFromOutsideEvents(localEventModels, rubeusEventModels);

        // Sincroniza eventos da Google com o banco de dados
        updateLocalEventsFromOutsideEvents(localEventModels, googleEventModels);

        return localEventModels;
    }

    private void updateLocalEventsFromOutsideEvents(
            ArrayList<EventModel> localEventModels, ArrayList<EventModel> outsideEventModels
    ) {
        for (EventModel outsideEventModel : outsideEventModels) {
            if (!localEventModels.contains(outsideEventModel)) {
                EventModel newEventModel = eventsData.createNewEvent(outsideEventModel);
                localEventModels.add(newEventModel);
            }
        }
    }
}
