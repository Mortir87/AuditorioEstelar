package com.asg.auditorioestelar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
public class RetrofitClient {
    //private static final String BASE_URL = "https://wants-terrain-canvas-scholarships.trycloudflare.com/"; server aitor
     private static final String BASE_URL = "http://10.0.2.2/teatro/config/"; // sin uso cambiar si se cierra el server
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    //.addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}