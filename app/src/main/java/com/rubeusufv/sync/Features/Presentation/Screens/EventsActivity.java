package com.rubeusufv.sync.Features.Presentation.Screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.rubeusufv.sync.Features.Domain.Models.Event;
import com.rubeusufv.sync.Features.Domain.Usecases.EventUsecases;
import com.rubeusufv.sync.Features.Presentation.Screens.Adapters.EventDayListAdapter;
import com.rubeusufv.sync.Features.Presentation.Screens.ListItems.EventDayListItem;
import com.rubeusufv.sync.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class EventsActivity extends Activity {
    EventDayListAdapter eventDayListAdapter;
    ArrayList<Event> eventList;
    ArrayList<EventDayListItem> eventDayList;
    Map<Date, ArrayList<Event>> eventsPerDayMap;
    EventUsecases usecases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        usecases = EventUsecases.getInstance();
        eventList = usecases.fetchEvents();

        eventsPerDayMap = new TreeMap<Date, ArrayList<Event>>();
        for (Event e : eventList) {
            ArrayList<Event> eventList = eventsPerDayMap.get(e.getDate());
            if (eventList == null) eventList = new ArrayList<Event>();
            eventList.add(e);
            eventsPerDayMap.put(e.getDate(), eventList);
        }

        eventDayList = new ArrayList<EventDayListItem>();
        for (Date date : eventsPerDayMap.keySet()) {
            eventDayList.add(new EventDayListItem(
                    date, eventsPerDayMap.get(date)
            ));
        }


        ListView eventDayListView = findViewById(R.id.eventDayList);
        eventDayListAdapter = new EventDayListAdapter(
            getBaseContext(), R.layout.event_day_list_item, eventDayList
        );
        eventDayListView.setAdapter(eventDayListAdapter);
    }

    public void openEventCreationScreen(View v) {
        Intent it = new Intent(getBaseContext(), CreateTask.class);
        startActivity(it);
    }
}