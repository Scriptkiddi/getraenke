package com.quappi.scriptkiddi.getraenke.controller;

import android.content.Context;
import android.util.Log;

import com.quappi.scriptkiddi.getraenke.api.DosService;
import com.quappi.scriptkiddi.getraenke.utils.Drink;
import com.quappi.scriptkiddi.getraenke.utils.Person;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by michel on 7/9/17.
 */

public class DrinkBuyAction {
    private Person person;
    private Drink drink;
    private boolean valid = true;
    private static String TAG = "DrinkBuyAction";
    public DrinkBuyAction(Person person, Drink drink) {
        this.person = person;
        this.drink = drink;
    }

    public Person getPerson() {
        return person;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public String toString() {
        return "DrinkBuyAction{" +
                "person=" + person +
                ", drink=" + drink +
                ", valid=" + valid +
                '}';
    }

    public void putOrder(Context context) {
        if (valid) {

            DosService.getInstance(context).orderDrink(drink.getEan(), person.getUsername()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.e(TAG, call.request().url().toString());
                    Log.e(TAG, Integer.toString(response.code()));
                    if(response.isSuccessful()){
                        Log.e(TAG, "asdf");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                }
            });

            personController.refresh(person.getUsername(), context);
            //TODO update credit? and display in drinks view
        }
    }
}
