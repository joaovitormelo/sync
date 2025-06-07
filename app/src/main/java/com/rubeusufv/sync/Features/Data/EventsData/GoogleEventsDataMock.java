package com.rubeusufv.sync.Features.Data.EventsData;

import com.rubeusufv.sync.Core.Exceptions.GoogleException;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;

import java.util.ArrayList;

public class GoogleEventsDataMock implements EventsDataContract {
    @Override
    public ArrayList<EventModel> viewEvents(UserModel user, int year, int month) {
        return new ArrayList<EventModel>();
    }

    @Override
    public EventModel createNewEvent(UserModel user, EventModel event) {
        //return EventModel.getMock();
        throw new GoogleException("Erro", "erro");
    }

    @Override
    public void updateEvent(UserModel user, EventModel event) {

    }

    @Override
    public void removeEvent(UserModel user, EventModel event) {

    }
}
