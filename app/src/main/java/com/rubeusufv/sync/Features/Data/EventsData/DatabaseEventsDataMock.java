package com.rubeusufv.sync.Features.Data.EventsData;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;

import java.util.ArrayList;

public class DatabaseEventsDataMock implements EventsDataContract {
    @Override
    public ArrayList<EventModel> viewEvents(int year, int month) {
        return new ArrayList<EventModel>();
    }

    @Override
    public EventModel createNewEvent(EventModel event) {
        return EventModel.getMock();
    }

    @Override
    public void updateEvent(EventModel event) {

    }

    @Override
    public void removeEvent(EventModel event) {

    }
}
