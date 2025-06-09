package com.rubeusufv.sync.Features.Data.EventsData;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;

import java.util.ArrayList;

public class RubeusEventsDataMock implements EventsDataContract {
    @Override
    public ArrayList<EventModel> viewEvents(UserModel user, int year, int month) {
        //return new ArrayList<EventModel>();
       /* EventModel e1 = EventModel.getMock();
        e1.setRubeusImported(true);
        e1.setId(1);
        e1.setRubeusId(2);*/
        ArrayList<EventModel> eventModelList = new ArrayList<EventModel>();
        //eventModelList.add(e1);
        return eventModelList;
    }

    @Override
    public EventModel createNewEvent(UserModel user, EventModel event) {
        event.setRubeusId(3);
        event.setRubeusImported(true);
        return event;
    }

    @Override
    public void updateEvent(UserModel user, EventModel event) {

    }

    @Override
    public void removeEvent(UserModel user, EventModel event) {

    }
}
