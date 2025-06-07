package com.rubeusufv.sync.Features.Presentation.Screens;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rubeusufv.sync.R;

public class Task extends AppCompatActivity {

    private TextView textViewStatus;
    private TextView textViewContactsTitle;
    private TextView textViewContacts;
    private TextView textViewError;

    //private static final String TAG = "RUBEUS_API_TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        //RubeusApiClient.configurarCredenciais("7", "9e5199c5de1c58f31987f71dde804da8");
//        initViews();
//        processarDadosRecebidos();
        //testarConexaoRubeus();
    }

//    private void initViews() {
//        textViewStatus = findViewById(R.id.textViewStatus);
//        textViewContactsTitle = findViewById(R.id.textViewContactsTitle);
//        textViewContacts = findViewById(R.id.textViewContacts);
//        textViewError = findViewById(R.id.textViewError);
//    }

//    private void processarDadosRecebidos() {
//        Intent intent = getIntent();
//        boolean success = intent.getBooleanExtra("success", false);
//        if (success) {
//            ArrayList<Contato> contatos = (ArrayList<Contato>) intent.getSerializableExtra("contatos_list");
//            exibirContatos(contatos);
//        }
//    }

//    private void exibirContatos(ArrayList<Contato> contatos) {
//        textViewStatus.setText("✅ Conexão realizada com sucesso!");
//        textViewStatus.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
//
//        if (contatos != null && !contatos.isEmpty()) {
//            textViewContactsTitle.setVisibility(View.VISIBLE);
//            textViewContacts.setVisibility(View.VISIBLE);
//
//            StringBuilder contatosText = new StringBuilder();
//            contatosText.append("Total de contatos: ").append(contatos.size()).append("\n\n");
//
//            for (int i = 0; i < Math.min(contatos.size(), 5); i++) {
//                Contato contato = contatos.get(i);
//                contatosText.append("--- Contato ").append(i + 1).append(" ---\n");
//                contatosText.append("ID: ").append(contato.getId()).append("\n");
//                contatosText.append("Nome: ").append(contato.getNome() != null ? contato.getNome() : "N/A").append("\n");
//                contatosText.append("Email: ").append(contato.getEmail() != null ? contato.getEmail() : "N/A").append("\n");
//                contatosText.append("Telefone: ").append(contato.getTelefone() != null ? contato.getTelefone() : "N/A").append("\n");
//                contatosText.append("CPF: ").append(contato.getCpf() != null ? contato.getCpf() : "N/A").append("\n\n");
//            }
//
//            if (contatos.size() > 5) {
//                contatosText.append("... e mais ").append(contatos.size() - 5).append(" contatos.");
//            }
//
//            textViewContacts.setText(contatosText.toString());
//
//        } else {
//            textViewContactsTitle.setVisibility(View.VISIBLE);
//            textViewContacts.setVisibility(View.VISIBLE);
//            textViewContactsTitle.setText("Nenhum contato encontrado");
//            textViewContacts.setText("A API retornou uma lista vazia de contatos.");
//        }
//    }


//    public void voltarParaCreateTask(View view) {
//        finish();
//    }

//    public void testarNovamente(View view) {
//        Toast.makeText(this, "Testando novamente...", Toast.LENGTH_SHORT).show();
//        testarConexaoRubeus();
//    }

//    private void testarConexaoRubeus() {
//        textViewStatus.setText("Testando conexão...");
//        textViewContactsTitle.setVisibility(View.GONE);
//        textViewContacts.setVisibility(View.GONE);
//        textViewError.setVisibility(View.GONE);
//
//        RubeusAPI apiService = RubeusApiClient.getClient().create(RubeusAPI.class);
//        Call<ArrayList<Contato>> callContatos = apiService.getContatos();
//
//        callContatos.enqueue(new Callback<ArrayList<Contato>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Contato>> call, Response<ArrayList<Contato>> response) {
//                if (response.isSuccessful()) {
//                    ArrayList<Contato> contatos = response.body();
//                    Log.d(TAG, "Sucesso: " + (contatos != null ? contatos.size() : 0) + " contatos");
//                    exibirContatos(contatos);
//                } else {
//                    try {
//                        String errorBody = response.errorBody() != null ?
//                                response.errorBody().string() : "Erro desconhecido";
//                        Log.d(TAG, "Erro " + response.code() + ": " + errorBody);
//                    } catch (Exception e) {
//                        Log.d(TAG, "Erro na resposta: " + e.getMessage());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Contato>> call, Throwable t) {
//                Log.e(TAG, "Falha na conexão: " + t.toString());
//            }
//        });
//    }
}