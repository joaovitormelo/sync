package com.rubeusufv.sync.Features.Presentation.Screens;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.rubeusufv.sync.Core.Exceptions.ValidationException;
import com.rubeusufv.sync.Features.Data.Utils.TestApiRubeus.RubeusApiClient;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Types.Color;
import com.rubeusufv.sync.Features.Domain.Types.SyncDate;
import com.rubeusufv.sync.Core.Exceptions.DatabaseException;
import com.rubeusufv.sync.Core.Exceptions.UsecaseException;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.RegisterNewEventUsecase;
import com.rubeusufv.sync.Core.Injector;
import com.rubeusufv.sync.Features.Domain.Utils.DateParser;
import com.rubeusufv.sync.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateEventActivity extends AppCompatActivity {

    // COMPONENTES
    private TextView nameTask, dateTask, timeTask;
    private EditText descriptionTask;
    private MaterialButton dateButton, timeButtonStart, timeButtonEnd;
    private MaterialAutoCompleteTextView repeatDropdown, categoryDropdown;
    View colorDisplay;
    private String dateTaskString;
    private SyncDate eventDate;
    private MaterialDatePicker<Long> datePicker;
    CheckBox allDayCheckbox;
    CheckBox importToRubeusCheckbox;
    CheckBox importToGoogleCheckbox;
    ProgressBar createEventLoadingBar;
    Button saveButton, cancelButton;
    Drawable borderRed;
    // VARIÁVEIS
    Map<String, View[]> inputMap;
    boolean isEditMode = false;
    EventModel originalEvent;
    // CASOS DE USO
    RegisterNewEventUsecase registerNewEventUsecase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        initializeInstances();
        configureTimePickerStart();
        configureTimePickerEnd();
        configureRepeatDropdown();
        configureCategoryDropdown();
        configureDatePicker(null);
        configureEditMode();

        RubeusApiClient.configurarCredenciais("7", "9e5199c5de1c58f31987f71dde804da8");
    }

    private void configureEditMode() {
        Intent it = getIntent();
        if (it.getSerializableExtra("event") == null) return;
        EventModel event = (EventModel) it.getSerializableExtra("event");
        Log.d("TESTE", "EVENT: " + event.getTitle());
        isEditMode = true;
        originalEvent = event;
        loadFieldValues(event);
    }

    private void loadFieldValues(EventModel event) {
        nameTask.setText(event.getTitle());
        descriptionTask.setText(event.getDescription());
        configureDatePicker(DateParser.fromSyncDate(event.getDate()));
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
        colorDisplay = findViewById(R.id.eventColor);
        allDayCheckbox = findViewById(R.id.allDayCheckbox);
        importToGoogleCheckbox = findViewById(R.id.checkboxImportToGoogle);
        importToRubeusCheckbox = findViewById(R.id.checkboxImportToRubeus);
        createEventLoadingBar = findViewById(R.id.loadingSave);
        saveButton = findViewById(R.id.btnSave);
        cancelButton = findViewById(R.id.btnCancel);
        borderRed = getResources().getDrawable(R.drawable.border_red);
        registerNewEventUsecase = Injector.getInstance().getRegisterNewEventUsecase();
        initializeInputMap();
    }

    private void initializeInputMap() {
        inputMap = new HashMap<>();
        inputMap.put("title", new View[]{nameTask});
        inputMap.put("description", new View[]{descriptionTask});
        inputMap.put("date", new View[]{dateButton});
        inputMap.put("startHour", new View[]{timeButtonStart});
        inputMap.put("endHour", new View[]{timeButtonEnd});
        inputMap.put("repeat", new View[]{repeatDropdown});
        inputMap.put("category", new View[]{categoryDropdown});
        inputMap.put("imported", new View[]{importToRubeusCheckbox, importToGoogleCheckbox});
    }

    private void configureDatePicker(Date defaultDate) {
        // Criação do calendário e exibição na tela
        if (defaultDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(defaultDate);
            datePicker = MaterialDatePicker
                    .Builder
                    .datePicker()
                    .setTitleText("Escolha uma data")
                    .setSelection(calendar.getTimeInMillis())
                    .build();
        } else {
            datePicker = MaterialDatePicker
                .Builder
                .datePicker()
                .setTitleText("Escolha uma data")
                .build();
        }
        dateButton.setOnClickListener(v -> datePicker.show(getSupportFragmentManager(), "DATE_PICKER"));
        datePicker.addOnPositiveButtonClickListener(selection -> {
            dateButton.setText(datePicker.getHeaderText());
            // Formatação para ser usado na intent
            Date date = new Date(selection);
            eventDate = DateParser.dateToSyncDate(date);
        });
        if (defaultDate != null) {
            dateButton.setText(DateParser.formatDateForDatePicker(defaultDate));
            eventDate = DateParser.dateToSyncDate(defaultDate);
        }
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
        String[] categoryOptions = {"Selecione uma categoria", "Reunião", "Prova", "Lazer", "Trabalho"};
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categoryOptions);
        categoryDropdown.setAdapter(adapterCategory);
        categoryDropdown.setText("Selecione uma categoria", false);
        categoryDropdown.setKeyListener(null);
        categoryDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Color eventColor = EventModel.getColorFromCategory(categoryOptions[position]);
                setEventColor(eventColor);
            }
        });
        setEventColor(EventModel.getColorFromCategory(categoryOptions[0]));
    }

    private void setEventColor(Color color) {
        GradientDrawable bgDrawable = (GradientDrawable) colorDisplay.getBackground();
        bgDrawable.setColor(getResources().getColor(colorToResId(color)));
    }

    private int colorToResId(Color color) {
        switch (color) {
            case RED:
                return R.color.red;
            case BLUE:
                return R.color.blue;
            case GREEN:
                return R.color.green;
            case GRAY:
                return R.color.grey;
            case YELLOW:
                return R.color.yellow;
            case PURPLE:
                return R.color.purple;
            default:
                return R.color.white;
        }
    }

    public void onSaveClick(View v) {
        setCreateEventLoading();
        EventModel newEvent = buildEventModel();
        new Thread(() -> {
            callCreateEventUsecase(newEvent);
        }).start();
    }

    private EventModel buildEventModel() {
        // Captura os dados digitados
        String nameTaskString = nameTask.getText().toString();
        String descriptionTaskString = descriptionTask.getText().toString();
        String startTime = timeButtonStart.getText().toString();
        String endTime = timeButtonEnd.getText().toString();
        String selectedCategory = categoryDropdown.getText().toString();
        if (selectedCategory.equals("Selecione uma categoria")) {
            selectedCategory = null;
        }
        Color eventColor = EventModel.getColorFromCategory(selectedCategory);
        boolean allDay = allDayCheckbox.isChecked();
        boolean importToGoogle = importToGoogleCheckbox.isChecked();
        boolean importToRubeus = importToRubeusCheckbox.isChecked();

        return new EventModel(
                0,
                nameTaskString,
                descriptionTaskString,
                eventDate,
                startTime,
                endTime,
                allDay,
                eventColor,
                selectedCategory,
                importToRubeus,
                importToGoogle
        );
    }

    private void setCreateEventLoading() {
        createEventLoadingBar.setVisibility(View.VISIBLE);
        cancelButton.setEnabled(false);
    }

    private void setCreateEventLoaded() {
        createEventLoadingBar.setVisibility(View.GONE);
        cancelButton.setEnabled(true);
    }

    private void callCreateEventUsecase(EventModel newEvent) {
        // Chamada ao caso de uso
        try {
            registerNewEventUsecase.registerNewEvent(newEvent);
            runOnUiThread(this::finishCreateEvent);
        } catch(Exception error) {
            runOnUiThread(() -> {
                handleCreateEventError(error);
            });
        }
    }

    private void resetAllFields() {
        for (Map.Entry<String, View[]> entry : inputMap.entrySet()) {
            View[] fields = entry.getValue();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setBackground(null);
            }
        }
    }

    private void handleCreateEventError(Exception error) {
        setCreateEventLoaded();
        resetAllFields();
        if (error instanceof ValidationException) {
            Toast.makeText(
                getBaseContext(), error.getMessage(), Toast.LENGTH_SHORT
            ).show();
            View[] fields = inputMap.get(((ValidationException) error).getField());
            for (int i = 0; i < fields.length; i++) {
                fields[i].setBackgroundTintList(null);
                fields[i].setBackground(borderRed);
            }
        }
    }

    private void finishCreateEvent() {
        setCreateEventLoaded();
        finish();
    }

    public void onCancelClick(View v) {
        finish();
    }
}
