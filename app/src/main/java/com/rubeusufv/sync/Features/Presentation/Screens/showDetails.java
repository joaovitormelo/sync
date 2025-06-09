package com.rubeusufv.sync.Features.Presentation.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.rubeusufv.sync.Core.Injector;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.ViewEventsUsecase;
import com.rubeusufv.sync.Features.Domain.Utils.DateParser;
import com.rubeusufv.sync.R;

public class showDetails extends AppCompatActivity {

    private TextView title, description, date, time, location, category, notification, repeat, synced;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        initializeInstances();
        configureScreen();
    }

    private void initializeInstances() {
        title = (TextView) findViewById(R.id.textViewTitle);
        description = (TextView) findViewById(R.id.textViewDescription);
        date = (TextView) findViewById(R.id.textViewDate);
        time = (TextView) findViewById(R.id.textViewTime);
        location = (TextView) findViewById(R.id.textViewLocation);
        category = (TextView) findViewById(R.id.textViewCategory);
        notification = (TextView) findViewById(R.id.textViewNotification);
        repeat = (TextView) findViewById(R.id.textViewRepeat);
        synced = (TextView) findViewById(R.id.textViewSyncedWith);
    }

    private void configureScreen() {
        Intent it = getIntent();
        if (it.getSerializableExtra("event") == null) return;
        EventModel event = (EventModel) it.getSerializableExtra("event");

        assert event != null;
        loadFieldValues(event);
    }

    private void loadFieldValues(EventModel event) {
        title.setText(event.getTitle());
        description.setText(event.getDescription());
        date.setText(event.getDate().toString());

        String timeComplete = event.getStartHour() + " - " + event.getEndHour();
        time.setText(timeComplete);

        location.setText("Local");
        category.setText(event.getCategory());
        notification.setText("Ativada");
        repeat.setText("Não");

        String syncedWhere = "";
        if(event.isRubeusImported()){
            syncedWhere += "Rubeus ";
        }
        if(event.isGoogleImported()){
            syncedWhere += "Google";
        }
        synced.setText(syncedWhere);
    }
}