package com.rubeusufv.sync.Features.Presentation.Screens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.rubeusufv.sync.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateTask extends AppCompatActivity {

    // Todas as variáveis para criar uma tarefa
    private TextView nameTask, dateTask, timeTask;
    private EditText descriptionTask;
    private MaterialButton dateButton, timeButtonStart, timeButtonEnd;
    private MaterialAutoCompleteTextView repeatDropdown, categoryDropdown;
    private String dateTaskString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        nameTask = findViewById(R.id.editTextTaskTitle);
        descriptionTask = findViewById(R.id.editTextTaskDescription);
        //dateTask = findViewById(R.id.textViewDate);
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

            // Formatação para ser usado na intent
            Date date = new Date(selection);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            dateTaskString = sdf.format(date);
        });


        // Criação do horário inicial
        timeButtonStart= findViewById(R.id.btnTimePickerStart);
        MaterialTimePicker timePickerStart = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Escolha um horário")
                .build();
        timeButtonStart.setOnClickListener(v -> timePickerStart.show(getSupportFragmentManager(), "TIME_PICKER"));
        timePickerStart.addOnPositiveButtonClickListener(v -> {
            int hour = timePickerStart.getHour();
            int minute = timePickerStart.getMinute();
            @SuppressLint("DefaultLocale") String formattedTime = String.format("%02d:%02d", hour, minute);
            timeButtonStart.setText(formattedTime);
        });


        // Criação do horário final
        timeButtonEnd = findViewById(R.id.btnTimePickerEnd);
        MaterialTimePicker timePickerEnd = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Escolha um horário")
                .build();
        timeButtonEnd.setOnClickListener(v -> timePickerEnd.show(getSupportFragmentManager(), "TIME_PICKER"));
        timePickerEnd.addOnPositiveButtonClickListener(v -> {
            int hour = timePickerEnd.getHour();
            int minute = timePickerEnd.getMinute();
            @SuppressLint("DefaultLocale") String formattedTime = String.format("%02d:%02d", hour, minute);
            timeButtonEnd.setText(formattedTime);
        });

        // Opções de repetição
        repeatDropdown = findViewById(R.id.repeatDropdown);
        String[] repeatOptions = new String[] {
                "Não repetir",
                "Todos os dias",
                "Todas as semanas",
                "Todos os meses",
                "Personalizar..."
        };
        ArrayAdapter<String> adapterRepeat = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, repeatOptions);
        repeatDropdown.setAdapter(adapterRepeat);
        repeatDropdown.setText("Não repetir", false);


        // Opções de categoria
        categoryDropdown = findViewById(R.id.categoryDropdown);
        String[] categoryOptions = new String[] {
                "Reunião",
                "Prova",
                "Personalizar..."
        };
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categoryOptions);
        categoryDropdown.setAdapter(adapterCategory);
        categoryDropdown.setText("Selecione uma categoria", false);
    }

    public void receiveNotification(View v) {

    }

    public void createTask(View v) {
        // Converte todas as valores em string
        String nameTaskString = nameTask.getText().toString();
        String descriptionTaskString = descriptionTask.getText().toString();
        //String dateTaskString = dateTask.getText().toString();
        String timeTaskString = timeTask.getText().toString();

        Intent it = new Intent(getBaseContext(), Task.class);
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