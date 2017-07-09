package com.quappi.scriptkiddi.getraenke.controller;

import android.content.Context;
import android.util.Log;

import com.quappi.scriptkiddi.getraenke.api.DosService;
import com.quappi.scriptkiddi.getraenke.events.DrinkControllerInitFinished;
import com.quappi.scriptkiddi.getraenke.events.DrinkUpdated;
import com.quappi.scriptkiddi.getraenke.utils.Drink;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fritz on 09.07.17.
 */

public class drinkController {
    private final static String TAG = "DrinkController";
    private static HashMap<String, Drink> drinkHashMap = new HashMap<>();
    private static ArrayList<String> drinkList = new ArrayList<>();
    public static void init(final Context context) {
        DosService.getInstance(context).getDrinks().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {

                if (response.isSuccessful()){

                    for(String s: response.body()){
                        Log.e(TAG, s);
                        drinkList.add(s);
                        Log.e(TAG, "test");
                        DosService.getInstance(context).getDrink(s).enqueue(new Callback<Drink>() {
                            @Override
                            public void onResponse(Call<Drink> call, Response<Drink> response) {
                                Log.e(TAG, "test");
                                if (response.isSuccessful()){

                                    Drink p = response.body();
                                    //p.setSupplier(supplierController.get(p.getSupplierID()));
                                    //p.setPermissions(permissionsController.get(p.getPermissionGroup()));
                                    drinkHashMap.put(response.body().getEan(), p);
                                    EventBus.getDefault().post(new DrinkUpdated(response.body()));
                                }else{
                                    Log.e(TAG, "Errorcode "+Integer.toString(response.code()));
                                }
                                if(drinkList.size()==drinkHashMap.size()){
                                    EventBus.getDefault().post(new DrinkControllerInitFinished());
                                }
                            }

                            @Override
                            public void onFailure(Call<Drink> call, Throwable t) {
                                Log.e(TAG, t.getMessage());
                            }
                        });
                    }

                }

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public static Collection<? extends Drink> getAll() {
        return drinkHashMap.values();
    }

    public static Drink get(String ean){
        return drinkHashMap.get(ean);
    }
}
