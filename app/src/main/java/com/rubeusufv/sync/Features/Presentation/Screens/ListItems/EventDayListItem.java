package com.rubeusufv.sync.Features.Presentation.Screens.ListItems;

import com.rubeusufv.sync.Features.Domain.Models.Event;

import java.util.ArrayList;
import java.util.Date;

public class EventDayListItem {
    private Date date;
    private ArrayList<Event> eventList;

    public EventDayListItem(Date date, ArrayList<Event> eventList) {
        this.date = date;
        this.eventList = eventList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }


}
