package com.rubeusufv.sync;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CreateTask extends Activity {

    // Todas as variáveis para criar uma tarefa
    private TextView nameTask;
    private EditText descriptionTask;
    private TextView dateTask;
    private TextView timeTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
    }

    public void createTask(View v) {
        nameTask = findViewById(R.id.editTextTaskTitle);
        descriptionTask = findViewById(R.id.editTextTaskDescription);
        dateTask = findViewById(R.id.textViewDate);
        timeTask = findViewById(R.id.textViewTime);

        // Converte todas as valores em string
        String nameTaskString = nameTask.getText().toString();
        String descriptionTaskString = descriptionTask.getText().toString();
        String dateTaskString = dateTask.getText().toString();
        String timeTaskString = timeTask.getText().toString();

        Intent it = new Intent(getBaseContext(), MainActivity.class);
        Bundle newTask = new Bundle();

        // Coloca todos os valores no bundle
        newTask.putString("nameTask", nameTaskString);
        newTask.putString("descriptionTask", descriptionTaskString);
        newTask.putString("dateTask", dateTaskString);
        newTask.putString("timeTask", timeTaskString);

        // Intent para a tela inicial
        it.putExtras(newTask);
        startActivity(it);
    }

    public void cancelTask(View v) {
        finish();
    }
}