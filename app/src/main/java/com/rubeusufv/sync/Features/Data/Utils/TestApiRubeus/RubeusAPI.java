package com.rubeusufv.sync.Features.Data.Utils.TestApiRubeus;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RubeusAPI {
    @GET("api/Contato/dadosPessoa")
    Call<ContatoResponse> getContatos();

    @POST("api/Contato/dadosPessoa")
    Call<ContatoResponse> getContatoPorId(@Body ContatoRequest request);
}