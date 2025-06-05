package com.rubeusufv.sync.Features.Presentation.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

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
    }
}