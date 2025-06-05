package com.rubeusufv.sync.Features.Presentation.Screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.rubeusufv.sync.R;

public class Task extends Activity {

    // Variáveis da tarefa
    private TextView nameTask, descriptionTask, dateTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent it = getIntent();
        Bundle task = it.getExtras();

        nameTask = findViewById(R.id.textViewTitle);
        descriptionTask = findViewById(R.id.textViewDescription);
        dateTask = findViewById(R.id.textViewDate);

        assert task != null;
        nameTask.setText(task.getString("nameTask"));
        descriptionTask.setText(task.getString("descriptionTask"));
        dateTask.setText(task.getString("dateTask"));
    }
}