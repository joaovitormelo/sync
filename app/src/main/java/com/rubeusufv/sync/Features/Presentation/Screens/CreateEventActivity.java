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
import com.rubeusufv.sync.Features.Data.Utils.TestApiRubeus.RubeusApiClient;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Types.Color;
import com.rubeusufv.sync.Features.Domain.Types.SyncDate;
import com.rubeusufv.sync.Core.Exceptions.DatabaseException;
import com.rubeusufv.sync.Core.Exceptions.UsecaseException;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.RegisterNewEventUsecase;
import com.rubeusufv.sync.Core.Injector;
import com.rubeusufv.sync.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateEventActivity extends AppCompatActivity {

    // COMPONENTES
    private TextView nameTask, dateTask, timeTask;
    private EditText descriptionTask;
    private MaterialButton dateButton, timeButtonStart, timeButtonEnd;
    private MaterialAutoCompleteTextView repeatDropdown, categoryDropdown;
    private String dateTaskString;
    private SyncDate eventDate;
    private MaterialDatePicker<Long> datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        initializeInstances();
        configureDatePicker();
        configureTimePickerStart();
        configureTimePickerEnd();
        configureRepeatDropdown();
        configureCategoryDropdown();

        RubeusApiClient.configurarCredenciais("7", "9e5199c5de1c58f31987f71dde804da8");
    }

    private void initializeInstances() {
        nameTask = findViewById(R.id.editTextTaskTitle);
        descriptionTask = findViewById(R.id.editTextTaskDescription);
        //dateTask = findViewById(R.id.textViewDate);
        timeTask = findViewById(R.id.textViewTime);
        dateButton = findViewById(R.id.btnDatePicker);
        timeButtonStart = findViewById(R.id.btnTimePickerStart);
        timeButtonEnd = findViewById(R.id.btnTimePickerEnd);
        repeatDropdown = findViewById(R.id.repeatDropdown);
        categoryDropdown = findViewById(R.id.categoryDropdown);
    }

    private void configureDatePicker() {
        // Criação do calendário e exibição na tela
        datePicker = MaterialDatePicker
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
            eventDate = new SyncDate(date.getDate(), date.getMonth(), date.getYear());
        });
    }

    private void configureTimePickerStart() {
        //o horario inicial
        MaterialTimePicker timePickerStart = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Escolha um horário")
                .build();
        timeButtonStart.setOnClickListener(v -> timePickerStart.show(getSupportFragmentManager(), "TIME_PICKER_START"));
        timePickerStart.addOnPositiveButtonClickListener(v -> {
            int hour = timePickerStart.getHour();
            int minute = timePickerStart.getMinute();
            String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
            timeButtonStart.setText(formattedTime);
        });
    }

    private void configureTimePickerEnd() {
        // o horario final
        MaterialTimePicker timePickerEnd = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(13)
                .setMinute(0)
                .setTitleText("Escolha horário de fim")
                .build();
        timeButtonEnd.setOnClickListener(v -> timePickerEnd.show(getSupportFragmentManager(), "TIME_PICKER_END"));
        timePickerEnd.addOnPositiveButtonClickListener(v -> {
            int hour = timePickerEnd.getHour();
            int minute = timePickerEnd.getMinute();
            String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
            timeButtonEnd.setText(formattedTime);
        });
    }

    private void configureRepeatDropdown() {
        // Opções de repetição
        String[] repeatOptions = {
                "Não repetir", "Todos os dias", "Todas as semanas", "Todos os meses", "Personalizar..."
        };
        ArrayAdapter<String> adapterRepeat = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, repeatOptions);
        repeatDropdown.setAdapter(adapterRepeat);
        repeatDropdown.setText("Não repetir", false);
    }

    private void configureCategoryDropdown() {
        // Opções de categoria
        String[] categoryOptions = {"Reunião", "Prova", "Lazer", "Trabalho"};
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categoryOptions);
        categoryDropdown.setAdapter(adapterCategory);
        categoryDropdown.setText("Selecione uma categoria", false);
    }

    public void createTask(View v) {
        // Captura os dados digitados
        String nameTaskString = nameTask.getText().toString();
        String descriptionTaskString = descriptionTask.getText().toString();
        String startTime = timeButtonStart.getText().toString();
        String endTime = timeButtonEnd.getText().toString();
        String selectedCategory = categoryDropdown.getText().toString();

        // Define a cor com base na categoria
        Color eventColor = Color.BLUE; // Padrão
        switch (selectedCategory) {
            case "Prova":
                eventColor = Color.RED;
                break;
            case "Reunião":
                eventColor = Color.GREEN;
                break;
            case "Lazer":
                eventColor = Color.YELLOW;
                break;
            case "Trabalho":
                eventColor = Color.PURPLE;
                break;
        }

        EventModel newEvent = new EventModel(
                0,
                nameTaskString,
                descriptionTaskString,
                eventDate,
                startTime,
                endTime,
                false,
                eventColor,
                selectedCategory,
                true,
                false
        );

        // Chamada ao caso de uso
        try {
            RegisterNewEventUsecase registerNewEventUsecase = Injector.getInstance().getRegisterNewEventUsecase();
            registerNewEventUsecase.registerNewEvent(newEvent);

            // Fecha a tela se deu tudo certo
            finish();

        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (UsecaseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelTask(View v) {
        finish();
    }
}
