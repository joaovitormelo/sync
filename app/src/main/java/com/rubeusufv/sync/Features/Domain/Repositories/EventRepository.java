package com.rubeusufv.sync.Features.Domain.Repositories;
import com.rubeusufv.sync.Exceptions.SingletonViolationException;
import com.rubeusufv.sync.Features.Domain.Models.Event;

import java.util.ArrayList;

public final class EventRepository implements EventRepositoryContract {
    private static EventRepository instance;
    private ArrayList<Event> eventList;

    private EventRepository() {
        eventList = new ArrayList<Event>();
        eventList.add(new Event("Evento 1"));
        eventList.add(new Event("Evento 2"));
    }

    public static EventRepository createInstance(
    ) throws SingletonViolationException {
        if (instance != null) throw new SingletonViolationException();
        return instance = new EventRepository();
    }

    public static EventRepository getInstance() {
        return instance;
    }

    @Override
    public ArrayList<Event> fetchEvents() {
        return eventList;
    }
}

