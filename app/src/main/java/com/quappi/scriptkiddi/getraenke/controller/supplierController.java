package com.quappi.scriptkiddi.getraenke.controller;

import android.content.Context;
import android.util.Log;

import com.quappi.scriptkiddi.getraenke.api.DosService;
import com.quappi.scriptkiddi.getraenke.utils.Supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fritz on 09.07.17.
 */

public class supplierController {
    private final static String TAG = "SupplierController";
    private static HashMap<Integer, Supplier> supplierHashMap = new HashMap<>();
    private static ArrayList<Integer> supplierList = new ArrayList<>();

    public static void init(final Context context) {
        DosService.getInstance(context).getSuppliers().enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                if (response.isSuccessful()){
                    for(Integer s: response.body()){
                        supplierList.add(s);
                        DosService.getInstance(context).getSupplier(s).enqueue(new Callback<Supplier>() {
                            @Override
                            public void onResponse(Call<Supplier> call, Response<Supplier> response) {
                                if (response.isSuccessful()) {
                                    supplierHashMap.put(response.body().getId(), response.body());
                                }
                                if (supplierHashMap.size() ==  supplierList.size()){
                                    drinkController.init(context);
                                }
                            }

                            @Override
                            public void onFailure(Call<Supplier> call, Throwable t) {
                                Log.e(TAG, t.getMessage());
                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }
}
