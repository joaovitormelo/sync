package com.rubeusufv.sync.Features.Data;

import com.rubeusufv.sync.Features.Domain.Models.Event;
import com.rubeusufv.sync.Features.Domain.Types.Color;

import java.util.ArrayList;
import java.util.Date;

public class EventsData implements EventsDataContract {
    @Override
    public ArrayList<Event> fetchEvents(int month) {
        return new ArrayList<Event>();
    }

    @Override
    public Event createEvent(Event event) {
        return new Event(
            "Evento 1", "Descrição", new Date(), "09:00", "10:00",
            false, Color.BLUE, "A", true, false
        );
    }
}
