package com.rubeusufv.sync.Features.Data;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;

import java.util.ArrayList;

public interface EventsDataContract {
    ArrayList<EventModel> viewEvents(int month);
    EventModel createEvent(EventModel eventModel);
}
