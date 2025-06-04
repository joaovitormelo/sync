package com.rubeusufv.sync.Features.Presentation.Screens.ListItems;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;

import java.util.ArrayList;
import java.util.Date;

public class EventDayListItem {
    private Date date;
    private ArrayList<EventModel> eventModelList;

    public EventDayListItem(Date date, ArrayList<EventModel> eventModelList) {
        this.date = date;
        this.eventModelList = eventModelList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<EventModel> getEventList() {
        return eventModelList;
    }

    public void setEventList(ArrayList<EventModel> eventModelList) {
        this.eventModelList = eventModelList;
    }


}
