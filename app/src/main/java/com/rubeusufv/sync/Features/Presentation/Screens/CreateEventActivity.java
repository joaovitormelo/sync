package com.rubeusufv.sync.Features.Presentation.Screens;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
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
import com.rubeusufv.sync.Features.Data.Utils.ApiRubeus.CreateEventRequest;
import com.rubeusufv.sync.Features.Data.Utils.ApiRubeus.CreateEventResponse;
import com.rubeusufv.sync.Features.Data.Utils.ApiRubeus.CustomFields;
import com.rubeusufv.sync.Features.Data.Utils.ApiRubeus.EventData;
import com.rubeusufv.sync.Features.Data.Utils.ApiRubeus.Person;
import com.rubeusufv.sync.Features.Data.Utils.ApiRubeus.RubeusApi;
import com.rubeusufv.sync.Features.Data.Utils.ApiRubeus.RubeusApiClient;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Types.Color;
import com.rubeusufv.sync.Features.Domain.Types.ContactType;
import com.rubeusufv.sync.Features.Domain.Types.SyncDate;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.EditEventUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.RegisterNewEventUsecase;
import com.rubeusufv.sync.Core.Injector;
import com.rubeusufv.sync.Features.Domain.Utils.DateParser;
import com.rubeusufv.sync.R;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventActivity extends AppCompatActivity {

    // COMPONENTES
    private TextView nameTask, headerText;
    private EditText descriptionTask;
    private MaterialButton dateButton, timeButtonStart, timeButtonEnd;
    private MaterialAutoCompleteTextView repeatDropdown, categoryDropdown;
    View colorDisplay;
    private String dateTaskString;
    private SyncDate eventDate;
    private MaterialDatePicker<Long> datePicker;
    private MaterialTimePicker timePickerStart, timePickerEnd;
    CheckBox allDayCheckbox;
    CheckBox importToRubeusCheckbox;
    CheckBox importToGoogleCheckbox;
    ProgressBar createEventLoadingBar;
    Button saveButton, cancelButton;
    Drawable borderRed;
    // VARIÁVEIS
    String[] categoryOptions = {"Selecione uma categoria", "Reunião", "Prova", "Lazer", "Trabalho"};
    Map<String, View[]> inputMap;
    boolean isEditMode = false;
    EventModel originalEvent;
    // CASOS DE USO
    RegisterNewEventUsecase registerNewEventUsecase;
    EditEventUsecase editEventUsecase;
    // Api Rubeus
    private static final String TAG = "RUBEUS_API_TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Date now = new Date();
        initializeInstances();
        configureTimePickerStart(now.getHours(), now.getMinutes());
        configureTimePickerEnd(now.getHours(), now.getMinutes());
        configureRepeatDropdown();
        configureCategoryDropdown();
        configureDatePicker(null);
        configureEditMode();
        initializeUsecases();

        RubeusApiClient.configurarCredenciais("7", "9e5199c5de1c58f31987f71dde804da8");
        //createEvent(eventModel);
    }

    private void initializeUsecases() {
        registerNewEventUsecase = Injector.getInstance().getRegisterNewEventUsecase();
        editEventUsecase = Injector.getInstance().getEditEventUsecase();
    }

    private void configureEditMode() {
        Intent it = getIntent();
        if (it.getSerializableExtra("event") == null) return;
        headerText.setText("Editar evento");
        EventModel event = (EventModel) it.getSerializableExtra("event");
        isEditMode = true;
        originalEvent = event;
        loadFieldValues(event);
    }

    private void loadFieldValues(EventModel event) {
        nameTask.setText(event.getTitle());
        descriptionTask.setText(event.getDescription());
        configureDatePicker(DateParser.fromSyncDate(event.getDate()));
        setStartHourField(event.getStartHour());
        setEndHourField(event.getEndHour());
        allDayCheckbox.setChecked(event.isAllDay());
        importToGoogleCheckbox.setChecked(event.isGoogleImported());
        importToGoogleCheckbox.setEnabled(false);
        importToRubeusCheckbox.setChecked(event.isRubeusImported());
        importToRubeusCheckbox.setEnabled(false);
        selectCategory(event.getCategory());
    }

    private void selectCategory(String category) {
        categoryDropdown.setText(category, false);
        setEventColor(EventModel.getColorFromCategory(category));
    }

    private void setStartHourField(String startHour) {
        int[] startTime = DateParser.extractHourAndMinute(startHour);
        configureTimePickerStart(startTime[0], startTime[1]);
        timeButtonStart.setText(startHour);
    }

    private void setEndHourField(String endHour) {
        int[] endTime = DateParser.extractHourAndMinute(endHour);
        configureTimePickerEnd(endTime[0], endTime[1]);
        timeButtonEnd.setText(endHour);
    }

    private void initializeInstances() {
        headerText = findViewById(R.id.headerText);
        nameTask = findViewById(R.id.editTextTaskTitle);
        descriptionTask = findViewById(R.id.editTextTaskDescription);
        //dateTask = findViewById(R.id.textViewDate);
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
                    //teste de cor aqui
                    .setTheme(R.style.ThemeOverlay_MyDatePicker)
                    .setSelection(calendar.getTimeInMillis())
                    .build();
        } else {
            datePicker = MaterialDatePicker
                .Builder
                .datePicker()
                .setTitleText("Escolha uma data")
                  //teste de cor
                    .setTheme(R.style.ThemeOverlay_MyDatePicker)
                    .build();
        }
        dateButton.setOnClickListener(v -> datePicker.show(getSupportFragmentManager(), "DATE_PICKER"));
        datePicker.addOnPositiveButtonClickListener(selection -> {
            dateButton.setText(datePicker.getHeaderText());
            // Formatação para ser usado na intent
            Date date = new Date(selection);
            date.setDate(date.getDate() + 1);
            eventDate = DateParser.dateToSyncDate(date);
        });
        if (defaultDate != null) {
            dateButton.setText(DateParser.formatDateForDatePicker(defaultDate));
            eventDate = DateParser.dateToSyncDate(defaultDate);
        }
    }

    private void configureTimePickerStart(int defaultHour, int defaultMinute) {


        timePickerStart = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(defaultHour)
                .setMinute(defaultMinute)
                .setTitleText("Escolha um horário")
                .setTheme(R.style.ThemeOverlay_MyTimePicker) // <-- Adiciona o tema
                .build();

        timeButtonStart.setOnClickListener(v -> timePickerStart.show(getSupportFragmentManager(), "TIME_PICKER_START"));

        timePickerStart.addOnPositiveButtonClickListener(v -> {
            int hour = timePickerStart.getHour();
            int minute = timePickerStart.getMinute();
            String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
            timeButtonStart.setText(formattedTime);
        });
    }


    private void configureTimePickerEnd(int defaultHour, int defaultMinute) {

        timePickerEnd = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(defaultHour)
                .setMinute(defaultMinute)
                .setTitleText("Escolha horário de fim")
                .setTheme(R.style.ThemeOverlay_MyTimePicker) // <-- Adiciona o tema
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
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categoryOptions);
        categoryDropdown.setAdapter(adapterCategory);
        categoryDropdown.setKeyListener(null);
        categoryDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Color eventColor = EventModel.getColorFromCategory(categoryOptions[position]);
                setEventColor(eventColor);
            }
        });
        selectCategory(categoryOptions[0]);
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
            callUsecase(newEvent);
        }).start();
        createEvent(newEvent);
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

    private void callUsecase(EventModel newEvent) {
        // Chamada ao caso de uso
        try {
            if (isEditMode) {
                callEditUsecase(newEvent);
            } else {
                callCreateUsecase(newEvent);
            }
            runOnUiThread(this::finishCreateEvent);
        } catch(Exception error) {
            runOnUiThread(() -> {
                handleCreateEventError(error);
            });
        }
    }

    private void callEditUsecase(EventModel event) {
        originalEvent.updateFromEditedEvent(event);
        editEventUsecase.editEvent(originalEvent);
    }

    private void callCreateUsecase(EventModel newEvent) {
        registerNewEventUsecase.registerNewEvent(newEvent);
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
        Toast.makeText(
                getBaseContext(), error.getMessage(), Toast.LENGTH_SHORT
        ).show();
        if (error instanceof ValidationException) {
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

    public void createEvent(EventModel eventModel) {
        RubeusApi apiService = RubeusApiClient.getClient().create(RubeusApi.class);

        Person pessoa = new Person("22", "22");

        CustomFields camposPersonalizados = new CustomFields(
                eventModel.getId(),
                eventModel.getUserId(),
                eventModel.getTitle(),
                eventModel.getDescription(),
                eventModel.getDate(),
                eventModel.getStartHour(),
                eventModel.getEndHour(),
                eventModel.isAllDay(),
                eventModel.getColor(),
                eventModel.getCategory(),
                eventModel.isRubeusImported(),
                eventModel.getRubeusId(),
                eventModel.getContactType(),
                eventModel.isGoogleImported(),
                eventModel.getGoogleId()
        );

        CreateEventRequest createEventRequest = new CreateEventRequest(
                "104",
                "1",
                "104",
                eventModel.getDescription(),
                pessoa,
                camposPersonalizados,
                "7",
                "9e5199c5de1c58f31987f71dde804da8"
        );

        // Executar a chamada síncrona em uma thread separada
        new Thread(() -> {
            Call<CreateEventResponse> callCreateEvent = apiService.sendEvent(createEventRequest);
            try {
                Response<CreateEventResponse> response = callCreateEvent.execute(); // SÍNCRONO

                if (response.isSuccessful()) {
                    CreateEventResponse createEventResponse = response.body();
                    if (createEventResponse != null && createEventResponse.isSuccess()) {
                        EventData eventData = createEventResponse.getData();
                        Log.d(TAG, "Evento criado com sucesso na API da Rubeus!");
                        Log.d(TAG, "Id: " + eventData.getId());
                        Log.d(TAG, "Description: " + eventData.getDescricao());
                        Log.d(TAG, "Momento: " + eventData.getMomento());
                        Log.d(TAG, "TipoNome: " + eventData.getTipoNome());
                    } else {
                        Log.e(TAG, "Erro ao criar evento: response nulo ou falha lógica");
                    }
                } else {
                    String errorBody = response.errorBody() != null ? response.errorBody().string() : "Erro desconhecido";
                    Log.e(TAG, "Erro " + response.code() + ": " + errorBody);
                }
            } catch (Exception e) {
                Log.e(TAG, "Erro na requisição síncrona: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

}
