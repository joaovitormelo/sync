package com.rubeusufv.sync.Features.Presentation.Screens;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private ViewEventsUsecase viewEventsUsecase;
    private Map<SyncDate, ArrayList<EventModel>> eventsPerDayMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.CalendarCustomTheme);
        super.onCreate(savedInstanceState);

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
        bottomNav.setSelectedItemId(R.id.nav_lista);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Log.d("TEST", String.valueOf(id));
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
            ArrayList<EventModel> eventos = map.get(date);

            Set<Integer> cores = new HashSet<>();
            for (EventModel e : eventos) {
                int cor = getCorID(e.getCategory());
                Log.d("CALENDAR_DEBUG", "Evento: " + e.getTitle() + " Categoria: " + e.getCategory() + " Cor ID: " + getCorID(e.getCategory()));

                cores.add(cor);
            }

            List<Integer> sorted = new ArrayList<>(cores);
            Collections.sort(sorted);

            String imageName = "marker";
            for (int c : sorted) {
                imageName += "_" + c;
            }

            int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
            Calendar calendar = Calendar.getInstance();
            calendar.set(date.getYear(), date.getMonth() - 1, date.getDay());
            markers.add(new EventDay(calendar, resId));
        }

        calendarView.setEvents(markers);
        setupClickListener();
    }

    private int getCorID(String categoria) {
        switch (categoria) {
            case EventModel.CATEGORY.TEST:
                return 2; // vermelho
            case EventModel.CATEGORY.REUNION:
                return 3; // verde
            case EventModel.CATEGORY.LEISURE:
                return 4; // amarelo
            case EventModel.CATEGORY.WORK:
                return 5; // roxo
            default:
                return 1; // azul
        }
    }

    private int getColorValue(int corID) {
        switch (corID) {
            case 2:
                return getColor(R.color.red);
            case 3:
                return getColor(R.color.green);
            case 4:
                return getColor(R.color.yellow);
            case 5:
                return getColor(R.color.purple);
            default:
                return getColor(R.color.blue);
        }
    }

    private void setupClickListener() {
        calendarView.setOnDayClickListener(eventDay -> {
            Calendar clickedDay = eventDay.getCalendar();

            SyncDate selectedDate = new SyncDate(
                    clickedDay.get(Calendar.DAY_OF_MONTH),
                    clickedDay.get(Calendar.MONTH) + 1,
                    clickedDay.get(Calendar.YEAR)
            );

            if (eventsPerDayMap != null && eventsPerDayMap.containsKey(selectedDate)) {
                ArrayList<EventModel> eventosDoDia = eventsPerDayMap.get(selectedDate);

                if (eventosDoDia == null || eventosDoDia.isEmpty()) {
                    Toast.makeText(this, "Sem eventos neste dia", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (eventosDoDia.size() == 1) {
                    EventModel evento = eventosDoDia.get(0);
                    Intent it = new Intent(this, showDetails.class);
                    it.putExtra("event", evento);
                    startActivity(it);
                } else {
                    String[] titulos = new String[eventosDoDia.size()];
                    int[] cores = new int[eventosDoDia.size()];

                    for (int i = 0; i < eventosDoDia.size(); i++) {
                        titulos[i] = eventosDoDia.get(i).getTitle();
                        cores[i] = getColorValue(getCorID(eventosDoDia.get(i).getCategory()));
                    }

                    new androidx.appcompat.app.AlertDialog.Builder(this)
                            .setTitle("Selecione um evento")
                            .setAdapter(new android.widget.ArrayAdapter<String>(this, android.R.layout.select_dialog_item, titulos) {
                                @Override
                                public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
                                    android.view.View view = super.getView(position, convertView, parent);
                                    ((TextView) view).setTextColor(cores[position]);
                                    return view;
                                }
                            }, (dialog, which) -> {
                                EventModel eventoSelecionado = eventosDoDia.get(which);
                                Intent it = new Intent(this, showDetails.class);
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
