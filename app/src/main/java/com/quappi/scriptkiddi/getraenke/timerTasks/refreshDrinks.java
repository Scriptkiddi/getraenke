package com.quappi.scriptkiddi.getraenke.timerTasks;

import android.content.Context;

import com.quappi.scriptkiddi.getraenke.api.DosService;

import java.util.List;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fritz on 09.07.17.
 */

public class refreshDrinks extends TimerTask {
    private Context context;
    public refreshDrinks(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        DosService.getInstance(context).getDrinks().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()){
                    //DrinkCache.getInstance(context).updateListDrinks(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }
}
