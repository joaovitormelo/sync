package com.rubeusufv.sync.Features.Data;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.User;

import java.util.ArrayList;

public interface RubeusDataContract {
    ArrayList<EventModel> viewEvents(int year, int month);
    User fetchUser(String login);
}
