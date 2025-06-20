package com.rubeusufv.sync.Features.Data.Utils.ApiRubeus;

public class CreateEventRequest {
    private String cod;
    private String tipo;
    private String codTipo;
    private String descricao;
    private Person pessoa;
    private CustomFields camposPersonalizados;
    private String origem;
    private String token;


    public CreateEventRequest(String cod, String tipo, String codTipo, String descricao, Person pessoa, CustomFields camposPersonalizados, String origem, String token) {
        this.cod = cod;
        this.tipo = tipo;
        this.codTipo = codTipo;
        this.descricao = descricao;
        this.pessoa = pessoa;
        this.camposPersonalizados = camposPersonalizados;
        this.origem = origem;
        this.token = token;
    }

    public String getCod() {return cod;}

    public void setCod(String cod) {this.cod = cod;}

    public String getTipo() {return tipo;}

    public void setTipo(String tipo) {this.tipo = tipo;}

    public String getCodTipo() {return codTipo;}

    public void setCodTipo(String codTipo) {this.codTipo = codTipo;}

    public String getDescricao() {return descricao;}

    public void setDescricao(String descricao) {this.descricao = descricao;}

    public Person getPerson() {return pessoa;}

    public void setPerson(Person pessoa) {this.pessoa = pessoa;}

    public CustomFields getCamposPersonalizados() {return camposPersonalizados;}

    public void setCamposPersonalizados(CustomFields camposPersonalizados) {this.camposPersonalizados = camposPersonalizados;}

    public String getOrigem() {return origem;}

    public void setOrigem(String origem) {this.origem = origem;}

    public String getToken() {return token;}

    public void setToken(String token) {this.token = token;}
}
