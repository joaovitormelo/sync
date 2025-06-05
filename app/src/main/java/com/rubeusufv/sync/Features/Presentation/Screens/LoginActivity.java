package com.rubeusufv.sync.Features.Presentation.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rubeusufv.sync.Core.Injector;
import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Usecases.EditEventUsecase;
import com.rubeusufv.sync.Features.Domain.Usecases.RegisterNewEventUsecase;
import com.rubeusufv.sync.R;

public class LoginActivity extends AppCompatActivity {
    public void onClickLogin(View v) {
        Intent intent = new Intent(getBaseContext(), EventsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);
        //Recuperando os id's
        EditText email=findViewById(R.id.editTextEmail);
        EditText password= findViewById(R.id.editTextSenha);
        CheckBox on_login=findViewById(R.id.checkboxSalvarSenha);
        Button login=findViewById(R.id.buttonLogin);
        Button newLogin=findViewById(R.id.buttonLogin);

        // Converte todas as valores em string
        String Emailstring = email.getText().toString();
        String PasswordString = password.getText().toString();

        //testEditEvent();
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
}