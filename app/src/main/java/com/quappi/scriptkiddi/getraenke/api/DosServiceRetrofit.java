package com.quappi.scriptkiddi.getraenke.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fritz on 09.07.17.
 */

public class DosServiceRetrofit {
    private static Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://35.156.87.172:9080/")
            .build();
    private static DosService service = retrofit.create(DosService.class);

    public static DosService getDosService(){
        return service;
    }
}
