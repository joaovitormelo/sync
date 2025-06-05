package com.rubeusufv.sync.Features.Data;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;

import java.util.ArrayList;

public interface GoogleDataContract {

    ArrayList<EventModel> viewEvents(int year, int month);
}
