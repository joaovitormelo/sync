package com.rubeusufv.sync.Features.Data;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;

import java.util.ArrayList;

public interface RubeusDataContract {
    ArrayList<EventModel> viewEvents(int year, int month);
    UserModel fetchUser(String login);
    EventModel createNewEvent(EventModel event);
    void updateEvent(EventModel event);
}
