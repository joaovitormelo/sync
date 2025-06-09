package com.rubeusufv.sync.Features.Data.EventsData;

import android.util.Log;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;
import com.rubeusufv.sync.Features.Domain.Types.Color;
import com.rubeusufv.sync.Features.Domain.Utils.DateParser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseEventsDataMock implements EventsDataContract {
    ArrayList<EventModel> eventList;
    int currentId = 3;

    public DatabaseEventsDataMock() {
        eventList = new ArrayList<EventModel>();
        EventModel e1 = EventModel.getMock();
        e1.setId(1);
        e1.setRubeusId(1);
        e1.setTitle("Aula 1");
        e1.setColor(Color.GREEN);

        EventModel e2 = EventModel.getMock();
        e2.setTitle("Visita Gabriel");
        e2.setColor(Color.YELLOW);
        e2.setId(2);
        e2.setRubeusId(5);

        eventList.add(e1);
        eventList.add(e2);
    }

    public ArrayList<EventModel> viewEvents(UserModel user, int year, int month) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException error) {
            // Simulated delay
        }

        List<EventModel> filtered = eventList.stream()
                .filter(event -> {
                    Date date = DateParser.fromSyncDate(event.getDate());
                    if (date == null) return false;

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);

                    int eventYear = cal.get(Calendar.YEAR);
                    int eventMonth = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based

                    return eventYear == year && eventMonth == month;
                })
                .collect(Collectors.toList());

        return new ArrayList<>(filtered);
    }

    @Override
    public EventModel createNewEvent(UserModel user, EventModel event) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException error) {
            // Simulated delay
        }

        event.setId(currentId++);
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException error) {
            // Simulated delay
        }
        eventList.removeIf(e -> e.getId() == event.getId());
    }
}
