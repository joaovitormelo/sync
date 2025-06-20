package com.rubeusufv.sync.Features.Data.Utils.TestApiRubeus;

public class ContatoRequest {
    private String token;
    private String origem;
    private String id;

    public ContatoRequest(String token, String origem, String id) {
        this.token = token;
        this.origem = origem;
        this.id = id;
    }

//    public String getToken() { return token; }
//    public void setToken(String token) { this.token = token; }
//
//    public String getOrigem() { return origem; }
//    public void setOrigem(String origem) { this.origem = origem; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
