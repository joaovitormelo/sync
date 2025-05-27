package com.rubeusufv.sync.Features.Domain.Repositories;
import com.rubeusufv.sync.Exceptions.SingletonViolationException;
import com.rubeusufv.sync.Features.Data.RubeusDataContract;
import com.rubeusufv.sync.Features.Domain.Models.Event;
import com.rubeusufv.sync.Features.Domain.Types.Color;

import java.util.ArrayList;
import java.util.Date;

public final class EventRepository implements EventRepositoryContract {
    private static EventRepository instance;
    private RubeusDataContract rubeusData;
    private ArrayList<Event> eventList;

    private EventRepository(RubeusDataContract rubeusData) {
        this.rubeusData = rubeusData;
        eventList = new ArrayList<Event>();
        eventList.add(new Event(
            "Evento 1", "Um evento", new Date(2025, 5, 10),
            "10:00", "11:00", false, Color.GREEN, "Tarefas",
            true, true
        ));
        eventList.add(new Event(
            "Evento 2", "Um outro evento", new Date(2025, 5, 8),
            "08:00", "09:00", false, Color.GREEN, "Atividades",
            false, true
        ));
        eventList.add(new Event(
                "Evento 3", "Um outro evento", new Date(2025, 5, 8),
                "09:00", "10:00", true, Color.BLUE, "Atividades",
                true, false
        ));
        eventList.add(new Event(
                "Evento 4", "Um outro evento", new Date(2025, 5, 11),
                "20:00", "21:00", true, Color.GREEN, "Atividades",
                true, false
        ));
    }

    public static EventRepository createInstance(
            RubeusDataContract rubeusData
    ) throws SingletonViolationException {
        if (instance != null) throw new SingletonViolationException();
        return instance = new EventRepository(rubeusData);
    }

    public static EventRepository getInstance() {
        return instance;
    }

    @Override
    public ArrayList<Event> fetchEvents() {
        ArrayList<Event> eventsRubeus = rubeusData.fetchEvents();
    }
}

