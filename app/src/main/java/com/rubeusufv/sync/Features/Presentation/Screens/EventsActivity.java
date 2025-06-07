package com.rubeusufv.sync.Features.Presentation.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.rubeusufv.sync.Core.Injector;
import com.rubeusufv.sync.Features.Data.Utils.TestApiRubeus.ContatoDados;
import com.rubeusufv.sync.Features.Data.Utils.TestApiRubeus.ContatoRequest;
import com.rubeusufv.sync.Features.Data.Utils.TestApiRubeus.ContatoResponse;
import com.rubeusufv.sync.Features.Data.Utils.TestApiRubeus.RubeusAPI;
import com.rubeusufv.sync.Features.Data.Utils.TestApiRubeus.RubeusApiClient;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Types.Month;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.ViewEventsUsecase;
import com.rubeusufv.sync.Features.Domain.Utils.DateParser;
import com.rubeusufv.sync.Features.Presentation.Adapters.EventDayListAdapter;
import com.rubeusufv.sync.Features.Presentation.Types.EventDayListItem;
import com.rubeusufv.sync.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsActivity extends AppCompatActivity {

    EventDayListAdapter eventDayListAdapter;
    ArrayList<EventModel> eventModelList;
    ArrayList<EventDayListItem> eventDayList;
    Map<Date, ArrayList<EventModel>> eventsPerDayMap;
    ViewEventsUsecase usecases;

    DrawerLayout drawerLayout;  // declare DrawerLayout aqui

    private static final String TAG = "RUBEUS_API_TEST";

    private void onClickExit(View v) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        drawerLayout = findViewById(R.id.drawer_layout);  // inicialize o DrawerLayout para tratar o menu

        usecases = Injector.getInstance().getEventUsecases();

        eventModelList = usecases.viewEvents(2025, Month.JANUARY);

        eventsPerDayMap = new TreeMap<Date, ArrayList<EventModel>>();
        for (EventModel e : eventModelList) {
            Date eventDate = DateParser.fromSyncDate(e.getDate());
            ArrayList<EventModel> eventModels = eventsPerDayMap.get(eventDate);
            if (eventModels == null) eventModels = new ArrayList<>();
            eventModels.add(e);

            Log.d("DATE", eventDate.toString());
            eventsPerDayMap.put(eventDate, eventModels);
        }

        eventDayList = new ArrayList<>();
        for (Date date : eventsPerDayMap.keySet()) {
            eventDayList.add(new EventDayListItem(date, eventsPerDayMap.get(date)));
        }

        ListView eventDayListView = findViewById(R.id.eventDayList);
        eventDayListAdapter = new EventDayListAdapter(
                getBaseContext(), R.layout.event_day_list_item, eventDayList
        );
        eventDayListView.setAdapter(eventDayListAdapter);

        // Configura o ícone customizado do ActionBar (hamburguer)
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_dehaze_24);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        NavigationView navigation = findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                Toast.makeText(getBaseContext(), "TESTE", Toast.LENGTH_SHORT).show();

                if (id == R.id.nav_logout) {
                    finish();
                }
                return true;
            }
        });

        // Teste da api da rubeus
        buscarContatoPorId("21");
    }

    // Teste da api da rubeus
    public void buscarContatoPorId(String id) {
        Log.d(TAG, "Iniciando busca por contato ID: " + id);
        RubeusAPI apiService = RubeusApiClient.getClient().create(RubeusAPI.class);
        ContatoRequest request = new ContatoRequest("9e5199c5de1c58f31987f71dde804da8", "7", id);
        Call<ContatoResponse> callContato = apiService.getContatoPorId(request);
        callContato.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ContatoResponse> call, Response<ContatoResponse> response) {
                Log.d(TAG, "Resposta recebida. Código: " + response.code());
                if (response.isSuccessful()) {
                    ContatoResponse contatoResponse = response.body();
                    if (contatoResponse != null) {
                        Log.d(TAG, "Success: " + contatoResponse.isSuccess());
                        if (contatoResponse.getDados() != null) {
                            ContatoDados dados = contatoResponse.getDados();
                            Log.d(TAG, "ID: " + dados.getId());
                            Log.d(TAG, "Nome: " + dados.getNome());
                            Log.d(TAG, "CPF: " + dados.getCpf());
                        } else {
                            Log.d(TAG, "Dados é null");
                        }
                    } else {
                        Log.d(TAG, "Response body é null");
                    }
                } else {
                    try {
                        String errorBody = response.errorBody() != null ?
                                response.errorBody().string() : "Erro desconhecido";
                        Log.e(TAG, "Erro " + response.code() + ": " + errorBody);
                    } catch (Exception e) {
                        Log.e(TAG, "Erro ao ler response: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ContatoResponse> call, Throwable t) {
                Log.e(TAG, "Falha na requisição: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        return true;
    }

    // Captura o clique no ícone da ActionBar para abrir/fechar o drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CharSequence title = item.getTitle();
        String text;
        if (title == null) text = "Abertura";
        else text = title.toString();
        Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT ).show();
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
}
