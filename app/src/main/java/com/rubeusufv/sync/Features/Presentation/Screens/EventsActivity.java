package com.rubeusufv.sync.Features.Presentation.Screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.rubeusufv.sync.Core.Injector;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Types.Month;
import com.rubeusufv.sync.Features.Domain.Usecases.ViewEventsUsecase;
import com.rubeusufv.sync.Features.Presentation.Screens.Adapters.EventDayListAdapter;
import com.rubeusufv.sync.Features.Presentation.Screens.ListItems.EventDayListItem;
import com.rubeusufv.sync.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class EventsActivity extends Activity {
    EventDayListAdapter eventDayListAdapter;
    ArrayList<EventModel> eventModelList;
    ArrayList<EventDayListItem> eventDayList;
    Map<Date, ArrayList<EventModel>> eventsPerDayMap;
    ViewEventsUsecase usecases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        usecases = Injector.getInstance().getEventUsecases();

        eventModelList = usecases.viewEvents(Month.JANUARY);

        eventsPerDayMap = new TreeMap<Date, ArrayList<EventModel>>();
        for (EventModel e : eventModelList) {
            ArrayList<EventModel> eventModelList = eventsPerDayMap.get(e.getDate());
            if (eventModelList == null) eventModelList = new ArrayList<EventModel>();
            eventModelList.add(e);
            eventsPerDayMap.put(e.getDate(), eventModelList);
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