package com.rubeusufv.sync.Features.Presentation.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.rubeusufv.sync.Core.Injector;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Types.Month;
import com.rubeusufv.sync.Features.Domain.Usecases.ViewEventsUsecase;
import com.rubeusufv.sync.Features.Presentation.Adapters.EventDayListAdapter;
import com.rubeusufv.sync.Features.Presentation.Types.EventDayListItem;
import com.rubeusufv.sync.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class EventsActivity extends AppCompatActivity {

    EventDayListAdapter eventDayListAdapter;
    ArrayList<EventModel> eventModelList;
    ArrayList<EventDayListItem> eventDayList;
    Map<Date, ArrayList<EventModel>> eventsPerDayMap;
    ViewEventsUsecase usecases;

    DrawerLayout drawerLayout;  // declare DrawerLayout aqui

    private void onClickExit(View v) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        drawerLayout = findViewById(R.id.drawer_layout);  // inicialize o DrawerLayout para tratar o menu

        usecases = Injector.getInstance().getEventUsecases();

        eventModelList = usecases.viewEvents(2025, Month.JANUARY);

        eventsPerDayMap = new TreeMap<>();
        for (EventModel e : eventModelList) {
            ArrayList<EventModel> eventModels = eventsPerDayMap.get(e.getDate());
            if (eventModels == null) eventModels = new ArrayList<>();
            eventModels.add(e);
            eventsPerDayMap.put(e.getDate(), eventModels);
        }

        eventDayList = new ArrayList<>();
        for (Date date : eventsPerDayMap.keySet()) {
            eventDayList.add(new EventDayListItem(date, eventsPerDayMap.get(date)));
        }

        ListView eventDayListView = findViewById(R.id.eventDayList);
        eventDayListAdapter = new EventDayListAdapter(
                getBaseContext(), R.layout.event_day_list_item, eventDayList
        );
        eventDayListView.setAdapter(eventDayListAdapter);

        // Configura o ícone customizado do ActionBar (hamburguer)
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_dehaze_24);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigation = findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                Toast.makeText(getBaseContext(), "TESTE", Toast.LENGTH_SHORT).show();

                if (id == R.id.nav_logout) {
                    finish();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        return true;
    }

    // Captura o clique no ícone da ActionBar para abrir/fechar o drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CharSequence title = item.getTitle();
        String text;
        if (title == null) text = "Abertura";
        else text = title.toString();
        Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT ).show();
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openEventCreationScreen(View v) {
        Intent it = new Intent(getBaseContext(), CreateEventActivity.class);
        startActivity(it);
    }
}
