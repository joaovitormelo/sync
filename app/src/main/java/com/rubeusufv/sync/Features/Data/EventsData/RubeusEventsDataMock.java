package com.rubeusufv.sync.Features.Data.EventsData;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;

import java.util.ArrayList;

public class RubeusEventsDataMock implements EventsDataContract {
    @Override
    public ArrayList<EventModel> viewEvents(UserModel user, int year, int month) {
        EventModel eventModel = EventModel.getMock();
        eventModel.setRubeusImported(true);
        ArrayList<EventModel> eventModelList = new ArrayList<EventModel>();
        eventModelList.add(eventModel);
        return eventModelList;
    }

    @Override
    public EventModel createNewEvent(UserModel user, EventModel event) {
        return EventModel.getMock();
    }

    @Override
    public void updateEvent(UserModel user, EventModel event) {

    }

    @Override
    public void removeEvent(UserModel user, EventModel event) {

    }
}
