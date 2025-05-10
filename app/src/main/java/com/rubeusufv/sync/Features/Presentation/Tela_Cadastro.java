package com.rubeusufv.sync.Features.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.rubeusufv.sync.R;

public class Tela_Cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);
        //Recuperando os id's
        EditText email=findViewById(R.id.EditEmail);
        EditText password= findViewById(R.id.EditPassword);
        CheckBox on_login=findViewById(R.id.CheckboxLogin);
        Button login=findViewById(R.id.ButtonLogin);
        Button newLogin=findViewById(R.id.buttonNewLogin);

        // Converte todas as valores em string
        String Emailstring = email.getText().toString();
        String PasswordString = password.getText().toString();

        //vai realizar a ação do botão de login para a tela create task
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tela_Cadastro.this, CreateTask.class);
                startActivity(intent);
             }
        });
        //Ainda esta levando para a mesma tela por que estamos sem tela de login
        newLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tela_Cadastro.this, CreateTask.class);
                startActivity(intent);
            }
        });
    }
}