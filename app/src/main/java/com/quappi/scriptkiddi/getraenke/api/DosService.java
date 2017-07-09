package com.quappi.scriptkiddi.getraenke.api;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.quappi.scriptkiddi.getraenke.Constants;
import com.quappi.scriptkiddi.getraenke.LoginCallback;
import com.quappi.scriptkiddi.getraenke.utils.Drink;
import com.quappi.scriptkiddi.getraenke.utils.Permissions;
import com.quappi.scriptkiddi.getraenke.utils.Person;
import com.quappi.scriptkiddi.getraenke.utils.Supplier;
import com.quappi.scriptkiddi.getraenke.utils.Token;
import com.quappi.scriptkiddi.getraenke.utils.exception.WrongPasswordException;

import org.greenrobot.eventbus.EventBus;

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
    private Context context;

    protected DosService(Context context) {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.baseURL)
                .build();
        service = retrofit.create(DosServiceInterface.class);

        this.context = context;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String userPassword = sharedPref.getString("UserPassword", "admin");
        String userName = sharedPref.getString("UserName", "admin");
        login(userName, userPassword);

    }

    public void login(String username, String password){

        String tokenHeader = String.format("Basic %s", Base64.encodeToString(String.format("%s:%s", username, password).getBytes(), Base64.NO_WRAP));
        Log.e(TAG, tokenHeader);
        service.getToken(tokenHeader).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Log.e(TAG, "token");
                    if (response.isSuccessful()) {
                        Log.e(TAG, "token_succ");
                        Log.d(TAG, response.body().getToken());
                        authHeader = "Bearer " + Base64.encodeToString(response.body().getToken().getBytes(), Base64.NO_WRAP);
                        Log.d(TAG, authHeader);
                    }else{
                        EventBus.getDefault().post(new WrongPasswordException());
                    }

            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void login(String username, String password, final LoginCallback loginCallback){

        String tokenHeader = String.format("Basic %s", Base64.encodeToString(String.format("%s:%s", username, password).getBytes(), Base64.NO_WRAP));

        service.getToken(tokenHeader).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                try {
                    if (response.isSuccessful()) {
                        Log.d(TAG, response.body().getToken());
                        authHeader = "Bearer " + Base64.encodeToString(response.body().getToken().getBytes(), Base64.NO_WRAP);
                        Log.d(TAG, authHeader);
                        loginCallback.onLoginSuccess();

                    } else {
                        loginCallback.onLoginFailed();
                    }
                }catch (Exception e){
                    Log.e(TAG, e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e(TAG, t.getMessage());
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
        Log.e(TAG, "header: "+authHeader);
        return service.listUsers(authHeader);
    }

    public Call<Drink> getDrink(String ean){
        return service.getDrink(ean, authHeader);
    }

    public Call<List<String>> getDrinks(){
        return service.listDrinks(authHeader);
    }

    public Call<List<String>> getPermissions(){
        return service.getPermissions(authHeader);
    }

    public Call<Permissions> getPermission(String permission){
        return service.getPermission(permission, authHeader);
    }

    public Call<List<Integer>> getSuppliers(){
        return service.getSuplliers(authHeader);
    }

    public Call<Supplier> getSupplier(int permission){
        return service.getSupplier(permission, authHeader);
    }

    public Call<Void> orderDrink(String ean){
        return service.orderDrink(ean, authHeader);
    }
    public Call<Void> orderDrink(String ean, String username){
        return service.orderDrink(ean, username, authHeader);
    }
}
