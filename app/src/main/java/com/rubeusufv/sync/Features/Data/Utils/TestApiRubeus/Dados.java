package com.rubeusufv.sync.Features.Data.Utils.TestApiRubeus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dados implements Serializable {
    public String id;
    public String contatoPrincipal;
    public List<String> contatosSecundarios = new ArrayList<String>();
    public String nome;

    public Dados(){}

//    public String getContatoPrincipal() {
//        return contatoPrincipal;
//    }
//
//    public void setContatoPrincipal(String contatoPrincipal) {
//        this.contatoPrincipal = contatoPrincipal;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public List<String> getContatosSecundarios() {
//        return contatosSecundarios;
//    }
//
//    public void setContatosSecundarios(List<String> contatosSecundarios) {
//        this.contatosSecundarios = contatosSecundarios;
//    }

//    public String getNome() {
//        return nome;
//    }
//
//    public void setNome(String nome) {
//        this.nome = nome;
//    }
}
