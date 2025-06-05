package com.rubeusufv.sync.Features.Data;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.User;

import java.util.ArrayList;

public class RubeusDataMock implements RubeusDataContract {
    @Override
    public ArrayList<EventModel> viewEvents(int year, int month) {
        EventModel eventModel = EventModel.getMock();
        eventModel.setRubeusSynchronized(true);
        ArrayList<EventModel> eventModelList = new ArrayList<EventModel>();
        eventModelList.add(eventModel);
        return eventModelList;
    }

    @Override
    public User fetchUser(String login) {
        return User.getMock();
    }
}
