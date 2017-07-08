package com.quappi.scriptkiddi.getraenke.timerTasks;

import com.quappi.scriptkiddi.getraenke.api.DosServiceRetrofit;
import com.quappi.scriptkiddi.getraenke.caches.DrinkCache;

import java.util.List;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fritz on 09.07.17.
 */

public class refreshDrinks extends TimerTask {
    @Override
    public void run() {
        DosServiceRetrofit.getDosService().listDrinks().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()){
                    DrinkCache.getInstance().updateListDrinks(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }
}
