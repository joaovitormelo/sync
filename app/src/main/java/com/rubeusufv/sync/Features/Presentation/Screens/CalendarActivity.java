package com.rubeusufv.sync.Features.Presentation.Screens;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rubeusufv.sync.Core.Injector;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Types.SyncDate;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.ViewEventsUsecase;
import com.rubeusufv.sync.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private ViewEventsUsecase viewEventsUsecase;
    private Map<SyncDate, ArrayList<EventModel>> eventsPerDayMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.CalendarCustomTheme);
        super.onCreate(savedInstanceState);

        // Força o idioma para pt-BR
        Locale locale = new Locale("pt", "BR");
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        setContentView(R.layout.activity_calendar);

        viewEventsUsecase = Injector.getInstance().getViewEventsUsecase();
        calendarView = findViewById(R.id.calendarView);

        setupBottomNavigation();
        loadEvents();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_calendario);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_lista) {
                startActivity(new Intent(this, EventsActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_add) {
                startActivity(new Intent(this, CreateEventActivity.class));
                return true;
            } else if (id == R.id.nav_calendario) {
                return true;
            }
            return false;
        });
    }

    private void loadEvents() {
        new Thread(() -> {
            // Carrega os eventos do ano atual inteiro
            int year = Calendar.getInstance().get(Calendar.YEAR);
            ArrayList<EventModel> allEvents = new ArrayList<>();
            for (int month = 1; month <= 12; month++) {
                allEvents.addAll(viewEventsUsecase.viewEvents(year, month));
            }

            eventsPerDayMap = new HashMap<>();

            for (EventModel e : allEvents) {
                SyncDate date = e.getDate();
                if (!eventsPerDayMap.containsKey(date)) {
                    eventsPerDayMap.put(date, new ArrayList<>());
                }
                eventsPerDayMap.get(date).add(e);
            }

            runOnUiThread(() -> markEventsOnCalendar(eventsPerDayMap));
        }).start();
    }

    private void markEventsOnCalendar(Map<SyncDate, ArrayList<EventModel>> map) {
        List<EventDay> markers = new ArrayList<>();

        for (SyncDate date : map.keySet()) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(date.getYear(), date.getMonth() - 1, date.getDay());
            markers.add(new EventDay(calendar, R.drawable.marker_event));
        }

        calendarView.setEvents(markers);
        setupClickListener();
    }

    private void setupClickListener() {
        calendarView.setOnDayClickListener(eventDay -> {
            Calendar clickedDay = eventDay.getCalendar();

            SyncDate selectedDate = new SyncDate(
                    clickedDay.get(Calendar.YEAR),
                    clickedDay.get(Calendar.MONTH) + 1,
                    clickedDay.get(Calendar.DAY_OF_MONTH)
            );

            if (eventsPerDayMap != null && eventsPerDayMap.containsKey(selectedDate)) {
                ArrayList<EventModel> eventosDoDia = eventsPerDayMap.get(selectedDate);

                if (eventosDoDia == null || eventosDoDia.isEmpty()) return;

                if (eventosDoDia.size() == 1) {
                    // se for apenas  1 evento vai abrir diretamente
                    EventModel evento = eventosDoDia.get(0);
                    Intent it = new Intent(this, ShowDetailsTask.class);
                    it.putExtra("event", evento);
                    startActivity(it);
                } else {
                    // se for vários eventos vai  mostra AlertDialog para escolher
                    String[] titulos = new String[eventosDoDia.size()];
                    for (int i = 0; i < eventosDoDia.size(); i++) {
                        titulos[i] = eventosDoDia.get(i).getTitle(); // ou outro campo visível
                    }

                    new androidx.appcompat.app.AlertDialog.Builder(this)
                            .setTitle("Selecione um evento")
                            .setItems(titulos, (dialog, which) -> {
                                EventModel eventoSelecionado = eventosDoDia.get(which);
                                Intent it = new Intent(this, ShowDetailsTask.class);
                                it.putExtra("event", eventoSelecionado);
                                startActivity(it);
                            })
                            .setNegativeButton("Cancelar", null)
                            .show();
                }
            } else {
                Toast.makeText(this, "Sem eventos neste dia", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
