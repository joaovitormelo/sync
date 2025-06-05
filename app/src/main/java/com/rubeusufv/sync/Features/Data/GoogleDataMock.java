package com.rubeusufv.sync.Features.Data;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;

import java.util.ArrayList;

public class GoogleDataMock implements GoogleDataContract {
    @Override
    public ArrayList<EventModel> viewEvents(int year, int month) {
        return new ArrayList<EventModel>();
    }
}
