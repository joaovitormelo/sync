package com.rubeusufv.sync;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

public class CreateTask extends AppCompatActivity {

    // Todas as variáveis para criar uma tarefa
    private TextView nameTask;
    private EditText descriptionTask;
    private TextView dateTask;
    private TextView timeTask;
    private MaterialButton dateButton;
    private MaterialButton timeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        nameTask = findViewById(R.id.editTextTaskTitle);
        descriptionTask = findViewById(R.id.editTextTaskDescription);
        dateTask = findViewById(R.id.textViewDate);
        timeTask = findViewById(R.id.textViewTime);
        dateButton = findViewById(R.id.btnDatePicker);

        // Criação do calendário e exibição na tela
        MaterialDatePicker<Long> datePicker = MaterialDatePicker
                .Builder
                .datePicker()
                .setTitleText("Escolha uma data")
                .build();
        dateButton.setOnClickListener(v -> datePicker.show(getSupportFragmentManager(), "DATE_PICKER"));
        datePicker.addOnPositiveButtonClickListener(selection -> {
            dateButton.setText(datePicker.getHeaderText());
        });


        // Criação do horário
        timeButton = findViewById(R.id.btnTimePicker);
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Escolha um horário")
                .build();
        timeButton.setOnClickListener(v -> timePicker.show(getSupportFragmentManager(), "TIME_PICKER"));
        timePicker.addOnPositiveButtonClickListener(v -> {
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            @SuppressLint("DefaultLocale") String formattedTime = String.format("%02d:%02d", hour, minute);
            timeButton.setText(formattedTime);
        });
    }

    public void receiveNotification(View v) {

    }

    public void createTask(View v) {
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