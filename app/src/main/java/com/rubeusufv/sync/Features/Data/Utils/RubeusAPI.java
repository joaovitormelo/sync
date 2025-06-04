package com.rubeusufv.sync.Features.Data.Utils;

import com.rubeusufv.sync.Features.Domain.Models.Event;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface RubeusAPI {

    @GET("api/Agendamento/listarAgendamentos")
    //Call<User> getEvents();

    @POST("api/Agendamento/cadastroApi")
    Call<Event> createEvent(
            @Body EventRequest eventRequest
    );
}