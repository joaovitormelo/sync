package com.rubeusufv.sync.Features.Data.Utils.ApiRubeus;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;

import java.util.List;

public class EventsResponse {
    private boolean success;
    private List<EventModel> dados;

    public boolean isSuccess() { return success; }

    public List<EventModel> getDados() { return dados; }
}
