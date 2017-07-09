package com.quappi.scriptkiddi.getraenke.api;

import com.quappi.scriptkiddi.getraenke.utils.Drink;
import com.quappi.scriptkiddi.getraenke.utils.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by fritz on 08.07.17.
 */

public interface DosServiceInterface {
    @GET("user")
    Call<List<String>> listUsers(@Header("Authorization") String authHeader);

    @GET("user/{user}")
    Call<Person> getUser(@Path("user") String user,@Header("Authorization") String authHeader);

    @GET("drink")
    Call<List<String>> listDrinks(@Header("Authorization") String authHeader);

    @GET("drink/{ean}")
    Call<Drink> getDrink(@Path("ean") String ean, @Header("Authorization") String authHeader);

    @GET("tokens")
    Call<String> getToken(@Header("Authorization") String base64UsernamePassword);
}
