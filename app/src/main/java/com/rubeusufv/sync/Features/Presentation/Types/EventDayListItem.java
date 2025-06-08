package com.rubeusufv.sync.Features.Presentation.Types;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Types.SyncDate;

import java.util.ArrayList;
import java.util.Date;

public class EventDayListItem {
    private SyncDate date;
    private ArrayList<EventModel> eventModelList;

    public EventDayListItem(SyncDate date, ArrayList<EventModel> eventModelList) {
        this.date = date;
        this.eventModelList = eventModelList;
    }

    public SyncDate getDate() {
        return date;
    }

    public void setDate(SyncDate date) {
        this.date = date;
    }

    public ArrayList<EventModel> getEventList() {
        return eventModelList;
    }

    public void setEventList(ArrayList<EventModel> eventModelList) {
        this.eventModelList = eventModelList;
    }
}
