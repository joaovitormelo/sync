package com.rubeusufv.sync.Features.Presentation.Screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.rubeusufv.sync.Features.Domain.Models.Event;
import com.rubeusufv.sync.Features.Domain.Usecases.EventUsecases;
import com.rubeusufv.sync.Features.Presentation.Screens.Adapters.EventListAdapter;
import com.rubeusufv.sync.R;
import com.rubeusufv.sync.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class EventsActivity2 extends Activity {
    ActivityMainBinding binding;
    EventListAdapter eventListAdapter;
    ArrayList<Event> eventList;
    EventUsecases usecases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events2);

        usecases = EventUsecases.getInstance();
        eventList = usecases.fetchEvents();

        ListView eventListView = findViewById(R.id.event_list_day_01);
        eventListAdapter = new EventListAdapter(
            getBaseContext(), R.layout.event_list_item, eventList
        );
        eventListView.setAdapter(eventListAdapter);
    }

    public void openEventCreationScreen(View v) {
        Intent it = new Intent(getBaseContext(), CreateTask.class);
        startActivity(it);
    }
}