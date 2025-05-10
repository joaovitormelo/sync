package com.rubeusufv.sync.Features.Domain.Repositories;

import com.rubeusufv.sync.Features.Domain.Models.Event;

import java.util.ArrayList;

public interface EventRepositoryContract {
    ArrayList<Event> fetchEvents();
}
