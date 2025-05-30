package com.rubeusufv.sync.Features.Data;

import com.rubeusufv.sync.Features.Domain.Models.Event;
import com.rubeusufv.sync.Features.Domain.Types.Month;

import java.util.ArrayList;

public interface RubeusDataContract {
    ArrayList<Event> fetchEvents(int month);
}
