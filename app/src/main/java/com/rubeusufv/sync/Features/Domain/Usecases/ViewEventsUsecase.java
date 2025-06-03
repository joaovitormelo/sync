package com.rubeusufv.sync.Features.Domain.Usecases;

import com.rubeusufv.sync.Features.Data.EventsDataContract;
import com.rubeusufv.sync.Features.Data.GoogleDataContract;
import com.rubeusufv.sync.Features.Data.RubeusDataContract;
import com.rubeusufv.sync.Features.Domain.Models.Event;

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

    //---------------------------CASOS DE USO-----------------------------------------

    public ArrayList<Event> viewEvents(int month) {
        ArrayList<Event> rubeusEvents = rubeusData.viewEvents(month);
        ArrayList<Event> googleEvents = googleData.viewEvents(month);
        ArrayList<Event> localEvents = eventsData.viewEvents(month);

        // Sincroniza eventos da Rubeus com o banco de dadps
        updateLocalEventsFromOutsideEvents(localEvents, rubeusEvents);

        // Sincroniza eventos da Google com o banco de dados
        updateLocalEventsFromOutsideEvents(localEvents, googleEvents);

        return localEvents;
    }

    //---------------------------FUNÇÕES AUXILIARES--------------------------------------

    private void updateLocalEventsFromOutsideEvents(
        ArrayList<Event> localEvents, ArrayList<Event> outsideEvents
    ) {
        for (Event rEvent : outsideEvents) {
            if (!localEvents.contains(rEvent)) {
                Event newEvent = eventsData.createEvent(rEvent);
                localEvents.add(newEvent);
            }
        }
    }
}
