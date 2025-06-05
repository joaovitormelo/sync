package com.rubeusufv.sync.Features.Data.EventsData;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;

import java.util.ArrayList;

public class RubeusEventsDataMock implements EventsDataContract {
    @Override
    public ArrayList<EventModel> viewEvents(int year, int month) {
        EventModel eventModel = EventModel.getMock();
        eventModel.setRubeusImported(true);
        ArrayList<EventModel> eventModelList = new ArrayList<EventModel>();
        eventModelList.add(eventModel);
        return eventModelList;
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
