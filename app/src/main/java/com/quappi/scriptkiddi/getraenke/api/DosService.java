package com.quappi.scriptkiddi.getraenke.api;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.quappi.scriptkiddi.getraenke.utils.Drink;
import com.quappi.scriptkiddi.getraenke.utils.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fritz on 09.07.17.
 */

public class DosService {
    private Retrofit retrofit;
    private DosServiceInterface service;
    private String authHeader;
    private String TAG="DosService";
    private static DosService instance = null;

    protected DosService(Context context) {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://35.156.87.172:9080/")
                .build();
        service = retrofit.create(DosServiceInterface.class);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String userPassword = sharedPref.getString("UserPassword", null);
        String userName = sharedPref.getString("UserName", null);

        String tokenHeader = Base64.encodeToString(String.format("Basic %s:%s", userName, userPassword).getBytes(), Base64.DEFAULT);

        service.getToken(tokenHeader).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, response.body());
                authHeader = "Bearer "+ Base64.encodeToString(response.body() .getBytes(), Base64.DEFAULT);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }

    public static DosService getInstance(Context context){
        if(instance==null){
            instance = new DosService(context);
        }
        return instance;
    }

    public Call<Person> getUser(String username){
        return service.getUser(username, authHeader);
    }

    public Call<List<String>> getUsers(){
        return service.listUsers(authHeader);
    }

    public Call<Drink> getDrink(String ean){
        return service.getDrink(ean, authHeader);
    }

    public Call<List<String>> getDrinks(){
        return service.listDrinks(authHeader);
    }
}
