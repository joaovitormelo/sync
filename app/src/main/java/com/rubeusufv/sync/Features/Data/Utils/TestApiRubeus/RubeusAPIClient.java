package com.rubeusufv.sync.Features.Data.Utils.TestApiRubeus;

import android.util.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RubeusAPIClient {

    public static final String BASE_URL = "https://crmufvgrupo7.apprubeus.com.br/";
    private static final String TAG = "RUBEUS_HTTP";
    private static Retrofit retrofit = null;
    private static String origem = "7";
    private static String token = "9e5199c5de1c58f31987f71dde804da8";

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request originalRequest = chain.request();

                        // Log da requisição
                        /*Log.d(TAG, "=== REQUISIÇÃO ===");
                        Log.d(TAG, "URL: " + originalRequest.url());
                        Log.d(TAG, "Método: " + originalRequest.method());
                        Log.d(TAG, "Headers: " + originalRequest.headers());*/

                        // Log do body se existir
                        if (originalRequest.body() != null) {
                            try {
                                Buffer buffer = new Buffer();
                                originalRequest.body().writeTo(buffer);
                                Log.d(TAG, "Body enviado: " + buffer.readUtf8());
                            } catch (Exception e) {
                                //Log.e(TAG, "Erro ao ler body: " + e.getMessage());
                            }
                        }

                        Request newRequest = originalRequest.newBuilder()
                                .header("Content-Type", "application/json")
                                .build();

                        // Executar requisição
                        okhttp3.Response response = chain.proceed(newRequest);

                        // Log da resposta
                        /*Log.d(TAG, "=== RESPOSTA ===");
                        Log.d(TAG, "Código: " + response.code());
                        Log.d(TAG, "Headers resposta: " + response.headers());*/

                        // Log do body da resposta
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            String responseString = responseBody.string();
                            //Log.d(TAG, "Body resposta: " + responseString);

                            // Recriar o response body para o Retrofit usar
                            ResponseBody newResponseBody = ResponseBody.create(
                                    responseBody.contentType(),
                                    responseString
                            );
                            response = response.newBuilder()
                                    .body(newResponseBody)
                                    .build();
                        }

                        return response;
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static void configurarCredenciais(String novaOrigin, String novoToken) {
        origem = novaOrigin;
        token = novoToken;
        retrofit = null;
    }
}