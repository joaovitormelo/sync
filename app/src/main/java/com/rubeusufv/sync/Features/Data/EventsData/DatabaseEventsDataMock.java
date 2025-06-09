package com.rubeusufv.sync.Features.Data.EventsData;

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
        e1.setRubeusId(1);
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
            Thread.sleep(2000);
        } catch (InterruptedException error) {
            // Simulated delay
        }

        return new ArrayList<>(eventList);  // Return a copy
    }

    @Override
    public EventModel createNewEvent(UserModel user, EventModel event) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException error) {
            // Simulated delay
        }

        eventList.add(event);
        return event;
    }

    @Override
    public void updateEvent(UserModel user, EventModel event) {
        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).getId() == event.getId()) {
                eventList.set(i, event);
                return;
            }
        }
    }

    @Override
    public void removeEvent(UserModel user, EventModel event) {
        eventList.removeIf(e -> e.getId() == event.getId());
    }
}
