package com.rubeusufv.sync.Features.Data;

import com.rubeusufv.sync.Features.Domain.Models.Event;

import java.util.ArrayList;

public interface EventsDataContract {
    ArrayList<Event> viewEvents(int month);
    Event createEvent(Event event);
}
