// ===============================
// EventsActivity.java (completo)
// ===============================
package com.rubeusufv.sync.Features.Presentation.Screens;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.rubeusufv.sync.Core.Injector;
import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Features.Data.Utils.ApiRubeus.EventsRequest;
import com.rubeusufv.sync.Features.Data.Utils.ApiRubeus.EventsResponse;
import com.rubeusufv.sync.Features.Data.Utils.ApiRubeus.RubeusApi;
import com.rubeusufv.sync.Features.Data.Utils.ApiRubeus.RubeusApiClient;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;
import com.rubeusufv.sync.Features.Domain.Types.SyncDate;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.ExcludeEventUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.ViewEventsUsecase;
import com.rubeusufv.sync.Features.Presentation.Adapters.CallbackEventListItem;
import com.rubeusufv.sync.Features.Presentation.Adapters.EventDayListAdapter;
import com.rubeusufv.sync.Features.Presentation.Types.EventDayListItem;
import com.rubeusufv.sync.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EventsActivity extends AppCompatActivity {
    private final String[] yearOptions = {"2025", "2024", "2023", "2022", "2021"};
    private final String[] monthOptions = {"Janeiro", "Fevereiro", "Março", "Maio", "Abril", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    private final String[] dayOptions = {
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
    };

    private TextInputLayout dropdownDayTextInput;
    private MaterialAutoCompleteTextView dayDropdown;
    int currentDayPos = 0;

    boolean isLoading = false;
    EventDayListAdapter eventDayListAdapter;
    ArrayList<EventModel> eventModelList;
    ArrayList<EventDayListItem> eventDayList;
    Map<SyncDate, ArrayList<EventModel>> eventsPerDayMap;

    ViewEventsUsecase viewEventsUsecase;
    ExcludeEventUsecase excludeEventUsecase;
    SessionManagerContract sessionManager;

    ListView eventDayListView;
    ProgressBar loadingEventsBar;
    DrawerLayout drawerLayout;
    private TextInputLayout dropdownYearTextInput, dropdownMonthTextInput;
    private MaterialAutoCompleteTextView yearDropdown, monthDropdown;
    int currentYearPos = 0, currentMonthPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        viewEventsUsecase = Injector.getInstance().getViewEventsUsecase();
        excludeEventUsecase = Injector.getInstance().getExcludeEventUsecase();
        sessionManager = Injector.getInstance().getSessionManager();

        eventDayListView = findViewById(R.id.eventDayList);
        loadingEventsBar = findViewById(R.id.loadingEventsBar);

        configureActionBar();
        configureDrawer();
        configureFilterDropdowns();
        configureBottomNav();
    }

    private void configureBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_lista);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_lista) {
                loadEventList();
                return true;
            } else if (id == R.id.nav_add) {
                startActivity(new Intent(this, CreateEventActivity.class));
                return true;
            } else if (id == R.id.nav_calendario) {
                startActivity(new Intent(this, CalendarActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
        // Teste da api da rubeus
        //buscarContatoPorId("21");

        // Api rubeus
        showEvents();
    }

    private void configureFilterDropdowns() {
        configureYearFilter();
        configureMonthFilter();
        //configureDayFilter();
    }

    private void configureYearFilter() {
        dropdownYearTextInput = findViewById(R.id.dropdownYearTextInput);
        yearDropdown = findViewById(R.id.dropdownYear);
        yearDropdown.setEnabled(false);
        ArrayAdapter<String> adapterRepeat = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, yearOptions);
        yearDropdown.setAdapter(adapterRepeat);
        yearDropdown.setKeyListener(null);
        int currentYear = new Date().getYear() + 1900;
        yearDropdown.setText(String.valueOf(currentYear), false);
        yearDropdown.setOnItemClickListener((parent, view, position, id) -> {
            currentYearPos = position;
            loadEventList();
        });
    }

    private void configureMonthFilter() {
        dropdownMonthTextInput = findViewById(R.id.dropdownMonthTextInput);
        monthDropdown = findViewById(R.id.dropdownMonth);
        ArrayAdapter<String> adapterRepeat = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, monthOptions);
        monthDropdown.setAdapter(adapterRepeat);
        monthDropdown.setKeyListener(null);
        int currentMonth = new Date().getMonth();
        monthDropdown.setText(monthOptions[currentMonth], false);
        currentMonthPos = currentMonth;
        monthDropdown.setOnItemClickListener((parent, view, position, id) -> {
            currentMonthPos = position;
            loadEventList();
        });
    }
    private void configureDayFilter() {
        dropdownDayTextInput = findViewById(R.id.dropdownDayTextInput);
        dayDropdown = findViewById(R.id.dropdownDay);

        ArrayAdapter<String> adapterDay = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, dayOptions);

        dayDropdown.setAdapter(adapterDay);
        dayDropdown.setKeyListener(null);

        // Define o dia atual como selecionado
        int currentDay = new Date().getDate() - 1;
        dayDropdown.setText(dayOptions[currentDay], false);
        currentDayPos = currentDay;

        dayDropdown.setOnItemClickListener((parent, view, position, id) -> {
            currentDayPos = position;
            loadEventList();
        });
    }


    private void configureDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        configureActionBar();

        UserModel sessionUser = sessionManager.getSessionUser();
        NavigationView navigation = findViewById(R.id.navigation_view);
        Menu menu = navigation.getMenu();
        MenuItem userNameText = menu.findItem(R.id.nav_name);
        userNameText.setTitle(sessionUser.getName());

        navigation.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_logout) {
                finish();
            }
            return true;
        });
    }

    private void configureActionBar() {
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_dehaze_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadEventList();
    }

    private void setEventsListLoading() {
        isLoading = true;
        loadingEventsBar.setVisibility(View.VISIBLE);
        eventDayListView.setVisibility(View.GONE);
        dropdownYearTextInput.setEnabled(false);
        dropdownMonthTextInput.setEnabled(false);
    }

    private void setEventsListLoaded() {
        isLoading = false;
        loadingEventsBar.setVisibility(View.GONE);
        eventDayListView.setVisibility(View.VISIBLE);
        dropdownYearTextInput.setEnabled(true);
        dropdownMonthTextInput.setEnabled(true);
    }

    private void loadEventList() {
        setEventsListLoading();
        int year = Integer.parseInt(yearOptions[currentYearPos]);
        new Thread(() -> callLoadEventsUsecase(year, currentMonthPos)).start();
        //String selectedDay = dayOptions[currentDayPos];
    }

    private void callLoadEventsUsecase(int year, int month) {
        eventModelList = viewEventsUsecase.viewEvents(year, month + 1);
        runOnUiThread(this::updateEventListView);
    }

    private void updateEventListView() {
        eventsPerDayMap = new TreeMap<>();
        for (EventModel e : eventModelList) {
            ArrayList<EventModel> eventModels = eventsPerDayMap.get(e.getDate());
            if (eventModels == null) eventModels = new ArrayList<>();
            eventModels.add(e);
            eventsPerDayMap.put(e.getDate(), eventModels);
        }

        eventDayList = new ArrayList<>();
        for (SyncDate date : eventsPerDayMap.keySet()) {
            eventDayList.add(new EventDayListItem(date, eventsPerDayMap.get(date)));
        }

        eventDayListAdapter = new EventDayListAdapter(
                getBaseContext(), R.layout.event_day_list_item, eventDayList,
                new CallbackEventListItem() {
                    @Override
                    public void onDeleteEvent(EventModel event) {
                        setEventsListLoading();
                        new Thread(() -> callDeleteEventUsecase(event)).start();
                    }

                    @Override
                    public void onEditEvent(EventModel event) {
                        Intent it = new Intent(getBaseContext(), CreateEventActivity.class);
                        it.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        it.putExtra("event", event);
                        startActivity(it);
                    }
                }, this);

        eventDayListView.setAdapter(eventDayListAdapter);
        setEventsListLoaded();
    }

    private void callDeleteEventUsecase(EventModel event) {
        try {
            excludeEventUsecase.excludeEvent(event, true, true);
            runOnUiThread(this::finishExcludeEvent);
        } catch (Exception error) {
            runOnUiThread(() -> handleErrorExcludeEvent(error));
        }
    }

    void finishExcludeEvent() {
        loadEventList();
    }

    void handleErrorExcludeEvent(Exception error) {
        setEventsListLoaded();
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }
// Tirei para sumir os 3 pontinhos desnecessários
   //  @Override
    //public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.drawer_menu, menu);
       // return true;
    //}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openEventCreationScreen(View v) {
        Intent it = new Intent(getBaseContext(), CreateEventActivity.class);
        startActivity(it);
    }

    public void showEvents() {
        RubeusApi apiService = RubeusApiClient.getClient().create(RubeusApi.class);
        EventsRequest eventsRequest = new EventsRequest("9e5199c5de1c58f31987f71dde804da8", "7");
        /*Call<EventsResponse> callEvents = apiService.getEvents(eventsRequest);

        // Executar em background!
        new Thread(() -> {
            try {
                Response<EventsResponse> response = callEvents.execute(); // Síncrono

                if (response.isSuccessful()) {
                    EventsResponse eventsResponse = response.body();
                    if (eventsResponse != null) {
                        Log.d(TAG, "Success: " + eventsResponse.isSuccess());
                        List<EventModel> events = eventsResponse.getDados();
                        if (events != null && !events.isEmpty()) {
                            for (EventModel eventModel : events) {
                                Log.d(TAG, "Id: " + eventModel.getId());
                                Log.d(TAG, "Título: " + eventModel.getTitle());
                                Log.d(TAG, "Código: " + eventModel.getDescription());
                                Log.d(TAG, "Origem: " + eventModel.getDate());
                                Log.d(TAG, "OrigemNome: " + eventModel.getDate());
                            }
                        } else {
                            Log.d(TAG, "Dados é null ou vazio");
                        }
                    } else {
                        Log.d(TAG, "Response body é null");
                    }
                } else {
                    String errorBody = response.errorBody() != null ? response.errorBody().string() : "Erro desconhecido";
                    Log.e(TAG, "Erro " + response.code() + ": " + errorBody);
                }
            } catch (Exception e) {
                Log.e(TAG, "Erro na requisição síncrona: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();*/
    }

}
