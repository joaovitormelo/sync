package com.rubeusufv.sync.Features.Data.EventsData;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;

import java.util.ArrayList;

public interface EventsDataContract {
    ArrayList<EventModel> viewEvents(int year, int month);
    EventModel createNewEvent(EventModel event);
    void updateEvent(EventModel event);
    void removeEvent(EventModel event);
}
