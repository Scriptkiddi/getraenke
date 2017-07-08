package com.quappi.scriptkiddi.getraenke.api;

import com.quappi.scriptkiddi.getraenke.utils.Drink;
import com.quappi.scriptkiddi.getraenke.utils.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by fritz on 08.07.17.
 */

public interface DosService {
    @GET("user")
    Call<List<String>> listUsers();

    @GET("user/{user}")
    Call<Person> getUser(@Path("user") String user);

    @GET("drink")
    Call<List<String>> listDrinks();

    @GET("drink/{ean}")
    Call<Drink> getDrink(@Path("ean") String ean);
}
