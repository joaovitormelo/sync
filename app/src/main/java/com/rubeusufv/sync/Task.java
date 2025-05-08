package com.rubeusufv.sync;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Task extends Activity {

    // Variáveis da tarefa
    private TextView nameTask, descriptionTask, dateTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent it = getIntent();
        Bundle task = it.getExtras();

        nameTask = findViewById(R.id.textViewTitleTask);
        descriptionTask = findViewById(R.id.textViewDescriptionTask);
        dateTask = findViewById(R.id.textViewDateTask);

        assert task != null;
        nameTask.setText(task.getString("nameTask"));
        descriptionTask.setText(task.getString("descriptionTask"));
        dateTask.setText(task.getString("dateTask"));

    }
}