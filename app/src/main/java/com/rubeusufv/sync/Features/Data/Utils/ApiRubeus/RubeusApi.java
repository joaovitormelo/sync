package com.rubeusufv.sync.Features.Data.Utils.ApiRubeus;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RubeusApi {

    // Busca os eventos na api
    @POST("api/Evento/listarTipoEvento")
    Call<EventsResponse> getEvents(@Body EventsRequest eventsRequest);

    // Cria um evento na api
    @POST("api/Evento/cadastro")
    Call<CreateEventResponse> sendEvent(@Body CreateEventRequest createEventRequest);

}
