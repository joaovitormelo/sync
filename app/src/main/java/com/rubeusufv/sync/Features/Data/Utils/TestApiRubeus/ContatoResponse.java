package com.rubeusufv.sync.Features.Data.Utils.TestApiRubeus;

public class ContatoResponse {
    private boolean success;
    private ContatoDados dados;

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public ContatoDados getDados() { return dados; }
    public void setDados(ContatoDados dados) { this.dados = dados; }
}
