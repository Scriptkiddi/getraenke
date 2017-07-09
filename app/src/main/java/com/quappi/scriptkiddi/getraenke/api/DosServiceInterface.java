package com.quappi.scriptkiddi.getraenke.api;

import com.quappi.scriptkiddi.getraenke.utils.Drink;
import com.quappi.scriptkiddi.getraenke.utils.Permissions;
import com.quappi.scriptkiddi.getraenke.utils.Person;
import com.quappi.scriptkiddi.getraenke.utils.Supplier;
import com.quappi.scriptkiddi.getraenke.utils.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by fritz on 08.07.17.
 */

public interface DosServiceInterface {
    @GET("users")
    Call<List<String>> listUsers(@Header("Authorization") String authHeader);

    @GET("users/{user}")
    Call<Person> getUser(@Path("user") String user,@Header("Authorization") String authHeader);

    @GET("drinks")
    Call<List<String>> listDrinks(@Header("Authorization") String authHeader);

    @GET("drinks/{ean}")
    Call<Drink> getDrink(@Path("ean") String ean, @Header("Authorization") String authHeader);

    @GET("tokens")
    Call<Token> getToken(@Header("Authorization") String base64UsernamePassword);

    @GET("permissions")
    Call<List<String>> getPermissions(@Header("Authorization") String base64UsernamePassword);

    @GET("permissions/{permission}")
    Call<Permissions> getPermission(@Path("permission") String permission, @Header("Authorization") String authHeader);

    @GET("suppliers")
    Call<List<Integer>> getSuplliers(@Header("Authorization") String base64UsernamePassword);

    @GET("suppliers/{supplier}")
    Call<Supplier> getSupplier(@Path("supplier") Integer supplier_id, @Header("Authorization") String authHeader);

    @POST("drinks/{ean}/order")
    Call<Void> orderDrink(@Path("ean") String ean, @Header("Authorization") String authHeader);

    @POST("drinks/{ean}/order")
    Call<Void> orderDrink(@Path("ean") String ean, @Query("username") String username, @Header("Authorization") String authHeader);

}
