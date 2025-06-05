package com.rubeusufv.sync.Features.Data;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;

import java.util.ArrayList;

public class RubeusDataMock implements RubeusDataContract {
    @Override
    public ArrayList<EventModel> viewEvents(int year, int month) {
        EventModel eventModel = EventModel.getMock();
        eventModel.setRubeusImported(true);
        ArrayList<EventModel> eventModelList = new ArrayList<EventModel>();
        eventModelList.add(eventModel);
        return eventModelList;
    }

    @Override
    public UserModel fetchUser(String login) {
        return UserModel.getMock();
    }

    @Override
    public EventModel createNewEvent(EventModel event) {
        return EventModel.getMock();
    }

    @Override
    public void updateEvent(EventModel event) {

    }
}
