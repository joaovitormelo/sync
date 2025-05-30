package com.rubeusufv.sync.Features.Domain.Usecases;

import com.rubeusufv.sync.Features.Data.EventsDataContract;
import com.rubeusufv.sync.Features.Data.GoogleDataContract;
import com.rubeusufv.sync.Features.Data.RubeusDataContract;
import com.rubeusufv.sync.Features.Domain.Models.Event;

import java.util.ArrayList;

public class EventUsecases {
    private RubeusDataContract rubeusData;
    private GoogleDataContract googleData;
    private EventsDataContract eventsData;

    public EventUsecases(
        RubeusDataContract rubeusData, GoogleDataContract googleData,
        EventsDataContract eventsData
    ) {
        this.rubeusData = rubeusData;
        this.googleData = googleData;
        this.eventsData = eventsData;
    }

    public ArrayList<Event> fetchEvents(int month) {
        ArrayList<Event> rubeusEvents = rubeusData.fetchEvents(month);
        ArrayList<Event> googleEvents = googleData.fetchEvents(month);
        ArrayList<Event> localEvents = eventsData.fetchEvents(month);

        // Sincroniza eventos da Rubeus com o banco de dadps
        updateLocalEventsFromOutsideEvents(localEvents, rubeusEvents);

        // Sincroniza eventos da Google com o banco de dados
        updateLocalEventsFromOutsideEvents(localEvents, googleEvents);

        return localEvents;
    }

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
