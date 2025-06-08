package com.rubeusufv.sync.Features.Presentation.Screens;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.rubeusufv.sync.Core.Exceptions.IncorrectPasswordException;
import com.rubeusufv.sync.Core.Exceptions.UserNotFoundException;
import com.rubeusufv.sync.Core.Injector;
import com.rubeusufv.sync.Core.Session.SessionManagerContract;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Usecases.Authentication.DoLoginUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.Authentication.EnterWithoutLoginUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.EditEventUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.ExcludeEventUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.ImportEventListToRepositoryUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.ImportSingleEventToRepositoryUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.Events.RegisterNewEventUsecase;
import com.rubeusufv.sync.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    //COMPONENTES
    private EditText emailEdit;
    private EditText passwordEdit;
    private CheckBox keepMeConnectedCheckbox;
    private Drawable greenBorder;
    private Drawable redBorder;
    private ProgressBar loginLoadingBar;
    private Button loginButton;
    //CASOS DE USO
    private DoLoginUsecase doLoginUsecase;
    private EnterWithoutLoginUsecase enterWithoutLoginUsecase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeInstances();

        setLoginLoading();
        new Thread(this::enterWithoutLogin).start();

        //testEditEvent();
        //testExcludeEvent();
        //testImportSingleEvent();
        //testImportEventList();
    }

    private void enterWithoutLogin() {
        boolean shouldEnter = enterWithoutLoginUsecase.enterWithoutLogin();
        runOnUiThread(() -> {
            finishEnterWithoutLogin(shouldEnter);
        });
    }

    private void finishEnterWithoutLogin(boolean shouldEnter) {
        setLoginLoaded();
        if (shouldEnter) {
            goToEventsScreen();
        }
    }

    private void initializeInstances() {
        emailEdit = findViewById(R.id.editTextEmail);
        passwordEdit = findViewById(R.id.editTextSenha);
        keepMeConnectedCheckbox = findViewById(R.id.checkboxKeepMeConnected);
        greenBorder = getResources().getDrawable(R.drawable.border_green);
        redBorder = getResources().getDrawable(R.drawable.border_red);
        loginLoadingBar = findViewById(R.id.loadingLoginBar);
        loginButton = findViewById(R.id.buttonLogin);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        doLoginUsecase = Injector.getInstance().getDoLoginUsecase();
        enterWithoutLoginUsecase = Injector.getInstance().getEnterWithoutLoginUsecase();
    }

    private void setLoginLoading() {
        loginLoadingBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);
    }

    private void setLoginLoaded() {
        loginLoadingBar.setVisibility(View.GONE);
        loginButton.setVisibility(View.VISIBLE);
    }

    void testImportEventList() {
        ImportEventListToRepositoryUsecase importUsecase = Injector.getInstance().getImportEventListToRepositoryUsecase();
        EventModel event1 = EventModel.getMock();
        EventModel event2 = EventModel.getMock();
        event1.setId(1);
        event1.setRubeusId(1);
        event1.setGoogleId(1);
        event1.setGoogleImported(true);
        event2.setTitle("Evento 2");
        event2.setGoogleImported(false);
        ArrayList<EventModel> eventList = new ArrayList<EventModel>();
        eventList.add(event1);
        eventList.add(event2);
        importUsecase.importEventListToRepositoryUsecase(eventList, true, true);
        Toast.makeText(getBaseContext(), "Eventos importados com sucesso!", Toast.LENGTH_SHORT).show();
    }

    void testImportSingleEvent() {
        ImportSingleEventToRepositoryUsecase importUsecase = Injector.getInstance().getImportSingleEventToRepositoryUsecase();
        EventModel event = EventModel.getMock();
        event.setId(1);
        event.setRubeusId(1);
        event.setGoogleId(1);
        event.setGoogleImported(true);
        importUsecase.importSingleEventToRepository(event, true, true);
        Toast.makeText(getBaseContext(), "Evento importado com sucesso!", Toast.LENGTH_SHORT).show();
    }

    void testEditEvent() {

        EditEventUsecase editEventUsecase = Injector.getInstance().getEditEventUsecase();
        EventModel event = EventModel.getMock();
        event.setId(1);
        event.setRubeusId(1);
        event.setGoogleId(1);
        editEventUsecase.editEvent(event);
        Toast.makeText(getBaseContext(), "Evento editado com sucesso!", Toast.LENGTH_SHORT).show();
    }

    void testExcludeEvent() {
        ExcludeEventUsecase excludeEventUsecase = Injector.getInstance().getExcludeEventUsecase();
        EventModel event = EventModel.getMock();
        event.setId(1);
        event.setGoogleId(1);
        excludeEventUsecase.excludeEvent(event, true, false);
        Toast.makeText(getBaseContext(), "Evento excluído com sucesso!", Toast.LENGTH_SHORT).show();
    }

    public void onClickLogin(View v) {
        // Converte todas as valores em string
        emailEdit.setBackground(greenBorder);
        passwordEdit.setBackground(greenBorder);
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        if (email.isEmpty()) {
            handleEmailError("Informe um e-mail!");
            return;
        }
        if (password.isEmpty()) {
            handlePasswordError("Informe uma senha!");
            return;
        }
        boolean keepMeConnected = keepMeConnectedCheckbox.isChecked();

        setLoginLoading();
        new Thread(() -> {
            doLogin(email, password, keepMeConnected);
        }).start();
    }

    private void doLogin(String email, String password, boolean keepMeConnected) {
        try {
            doLoginUsecase.doLogin(email, password, keepMeConnected);
            runOnUiThread(this::finishLogin);
        } catch(Exception error) {
            runOnUiThread(() -> {
                handleLoginError(error);
            });
        }
    }

    private void handleLoginError(Exception error) {
        setLoginLoaded();
        if (error instanceof UserNotFoundException) {
            handleEmailError("Usuário não encontrado!");
        } else if (error instanceof IncorrectPasswordException) {
            handlePasswordError("Senha incorreta!");
        } else {
            handleDefaultError(error.getMessage());
        }
    }

    private void handleEmailError(String message) {
        emailEdit.setBackground(redBorder);
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void handlePasswordError(String message) {
        passwordEdit.setBackground(redBorder);
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void handleDefaultError(String message) {
        emailEdit.setBackground(redBorder);
        passwordEdit.setBackground(redBorder);
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void finishLogin() {
        setLoginLoaded();
        goToEventsScreen();
    }

    private void goToEventsScreen() {
        Intent intent = new Intent(getBaseContext(), EventsActivity.class);
        startActivity(intent);
    }
}