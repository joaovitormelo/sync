package com.rubeusufv.sync.Features.Data.Utils.ApiRubeus;

public class EventData {
    private int id;
    private String descricao;
    private String momento;
    private String pessoa;
    private String tipo;
    private String tipoNome;
    private String imagem;
    private String origem;
    private String origemNome;


    public EventData(int id, String descricao, String momento, String pessoa, String tipo, String tipoNome, String imagem, String origem, String origemNome) {
        this.id = id;
        this.descricao = descricao;
        this.momento = momento;
        this.pessoa = pessoa;
        this.tipo = tipo;
        this.tipoNome = tipoNome;
        this.imagem = imagem;
        this.origem = origem;
        this.origemNome = origemNome;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getDescricao() {return descricao;}

    public String getMomento() {return momento;}

    public String getPessoa() {return pessoa;}

    public String getTipo() {return tipo;}

    public String getTipoNome() {return tipoNome;}

    public String getImagem() {return imagem;}

    public String getOrigem() {return origem;}

    public String getOrigemNome() {return origemNome;}

}
