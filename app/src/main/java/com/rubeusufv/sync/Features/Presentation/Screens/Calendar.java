package com.rubeusufv.sync.Features.Presentation.Screens;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.applandeo.materialcalendarview.CalendarDay;
import com.applandeo.materialcalendarview.CalendarView;
import com.google.android.material.datepicker.MaterialCalendar;
import com.rubeusufv.sync.R;

import java.util.ArrayList;
import java.util.List;

public class Calendar extends AppCompatActivity {

    private CalendarView calendarView;
    private List<CalendarDay> diasComEventos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calendar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        calendarView = findViewById(R.id.calendarView);

        carregarEventos();

        //calendarView.addDecorator(new EventDecorator(Color.RED, diasComEventos));

        /*calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                String dataSelecionada = date.getDay() + "/" + (date.getMonth() + 1) + "/" + date.getYear();

                Intent intent = new Intent(Calendar.this, ListaCompromissosActivity.class);
                intent.putExtra("data", dataSelecionada);
                startActivity(intent);
            }
        });*/
    }

    private void carregarEventos() {
        //isso aqui esta estatico só para testar
        //diasComEventos.add(CalendarDay.from(2025, 6, 17));
        //diasComEventos.add(CalendarDay.from(2025, 6, 20));
    }
}
