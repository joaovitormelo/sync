package com.rubeusufv.sync.Features.Domain.Usecases;

import com.rubeusufv.sync.Exceptions.SingletonViolationException;
import com.rubeusufv.sync.Features.Domain.Models.Event;
import com.rubeusufv.sync.Features.Domain.Repositories.EventRepositoryContract;

import java.util.ArrayList;

public final class EventUsecases {
    private static EventUsecases instance;
    private EventRepositoryContract repository;

    private EventUsecases(EventRepositoryContract repository) {
        this.repository = repository;
    }

    public static EventUsecases createInstance(
            EventRepositoryContract repository
    ) throws SingletonViolationException {
        if (instance != null) throw new SingletonViolationException();
        return instance = new EventUsecases(repository);
    }

    public static EventUsecases getInstance() {
        return instance;
    }

//--------------------------CASOS DE USO---------------------------------------

    public ArrayList<Event> fetchEvents() {
        return repository.fetchEvents();
    }
}
