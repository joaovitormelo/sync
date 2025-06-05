package com.rubeusufv.sync.Features.Data;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;

import java.util.ArrayList;

public class EventsDataMock implements EventsDataContract {
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
}
