package com.rubeusufv.sync.Features.Data;

import com.rubeusufv.sync.Features.Domain.Models.Event;

import java.util.ArrayList;

public class GoogleDataMock implements GoogleDataContract {
    @Override
    public ArrayList<Event> viewEvents(int month) {
        return new ArrayList<Event>();
    }
}
