package com.rubeusufv.sync.Features.Data;

import com.rubeusufv.sync.Features.Domain.Models.Event;
import java.util.ArrayList;

public class EventsDataMock implements EventsDataContract {
    @Override
    public ArrayList<Event> viewEvents(int month) {
        return new ArrayList<Event>();
    }

    @Override
    public Event createEvent(Event event) {
        return Event.getMock();
    }
}
