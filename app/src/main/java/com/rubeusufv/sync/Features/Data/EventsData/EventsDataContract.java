package com.rubeusufv.sync.Features.Data.EventsData;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;

import java.util.ArrayList;

public interface EventsDataContract {
    ArrayList<EventModel> viewEvents(UserModel user, int year, int month);
    EventModel createNewEvent(UserModel user, EventModel event);
    void updateEvent(UserModel user, EventModel event);
    void removeEvent(UserModel user, EventModel event);
}
