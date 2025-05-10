package com.rubeusufv.sync.Features.Domain.Repositories;
import com.rubeusufv.sync.Exceptions.SingletonViolationException;
import com.rubeusufv.sync.Features.Domain.Models.Event;
import com.rubeusufv.sync.Features.Domain.Types.Color;

import java.util.ArrayList;
import java.util.Date;

public final class EventRepository implements EventRepositoryContract {
    private static EventRepository instance;
    private ArrayList<Event> eventList;

    private EventRepository() {
        eventList = new ArrayList<Event>();
        eventList.add(new Event(
            "Evento 1", "Um evento", new Date(2025, 5, 10),
            "10:00", "11:00", false, Color.GREEN, "Tarefas",
            true, true
        ));
        eventList.add(new Event(
            "Evento 2", "Um outro evento", new Date(2025, 5, 8),
            "08:00", "09:00", false, Color.BLUE, "Atividades",
            false, true
        ));
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

