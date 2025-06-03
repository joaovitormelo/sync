package com.rubeusufv.sync.Features.Data;

import com.rubeusufv.sync.Features.Domain.Models.Event;

import java.util.ArrayList;

public class RubeusDataMock implements RubeusDataContract {
    public RubeusDataMock() {

    }

    @Override
    public ArrayList<Event> viewEvents(int month) {
        return new ArrayList<>();
    }

}
