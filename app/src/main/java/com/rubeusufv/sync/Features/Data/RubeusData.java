package com.rubeusufv.sync.Features.Data;

import com.rubeusufv.sync.Features.Domain.Models.Event;

import java.util.ArrayList;

public class RubeusData implements RubeusDataContract {
    public RubeusData() {

    }

    @Override
    public ArrayList<Event> fetchEvents(int month) {
        return new ArrayList<>();
    }

}
