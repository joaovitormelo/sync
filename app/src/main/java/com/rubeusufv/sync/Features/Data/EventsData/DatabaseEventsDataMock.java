package com.rubeusufv.sync.Features.Data.EventsData;

import android.util.Log;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;
import com.rubeusufv.sync.Features.Domain.Types.Color;

import java.util.ArrayList;

public class DatabaseEventsDataMock implements EventsDataContract {
    ArrayList<EventModel> eventList;

    public DatabaseEventsDataMock() {
        eventList = new ArrayList<EventModel>();
        EventModel e1 = EventModel.getMock();
        e1.setId(3);
        e1.setTitle("Aula 1");
        e1.setColor(Color.GREEN);
        EventModel e2 = EventModel.getMock();
        e2.setTitle("Visita Gabriel");
        e2.setColor(Color.YELLOW);
        e2.setId(4);
        eventList.add(e1);
        eventList.add(e2);
    }

    @Override
    public ArrayList<EventModel> viewEvents(UserModel user, int year, int month) {
        try {
            Thread.sleep(5000);
        } catch(InterruptedException error) {
            //
        }
        ArrayList<EventModel> list = new ArrayList<EventModel>();
        for (EventModel item : eventList) {
            list.add(item);
        }
        return list;
    }

    @Override
    public EventModel createNewEvent(UserModel user, EventModel event) {
        eventList.add(event);
        return event;
    }

    @Override
    public void updateEvent(UserModel user, EventModel event) {

    }

    @Override
    public void removeEvent(UserModel user, EventModel event) {

    }
}
