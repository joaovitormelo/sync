package com.rubeusufv.sync.Features.Presentation.Screens;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rubeusufv.sync.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setTheme(R.style.CalendarCustomTheme);

        super.onCreate(savedInstanceState);

        // tive que forçar o idioma para português (Brasil)
        Locale locale = new Locale("pt", "BR");
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);
        List<EventDay> events = new ArrayList<>();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2025, Calendar.JUNE, 17);
        events.add(new EventDay(calendar1, R.drawable.ic_launcher_foreground)); // esse marcador é so um exemplo para eu ver a visualização ainda esta mocado

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2025, Calendar.JUNE, 20);
        events.add(new EventDay(calendar2, R.drawable.ic_launcher_foreground));

        calendarView.setEvents(events);


        calendarView.setOnDayClickListener(eventDay -> {
            Calendar clickedDay = eventDay.getCalendar();

            String dataSelecionada = String.format(Locale.getDefault(),
                    "%02d/%02d/%d",
                    clickedDay.get(Calendar.DAY_OF_MONTH),
                    clickedDay.get(Calendar.MONTH) + 1,
                    clickedDay.get(Calendar.YEAR));

            Toast.makeText(CalendarActivity.this, "Data clicada: " + dataSelecionada, Toast.LENGTH_SHORT).show();

        });
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
}
