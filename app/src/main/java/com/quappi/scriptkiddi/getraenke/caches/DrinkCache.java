package com.quappi.scriptkiddi.getraenke.caches;

import android.content.Context;
import android.util.Log;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.quappi.scriptkiddi.getraenke.api.DosService;
import com.quappi.scriptkiddi.getraenke.events.DrinkUpdated;
import com.quappi.scriptkiddi.getraenke.utils.Drink;
import com.quappi.scriptkiddi.getraenke.utils.exception.NetworkErrorException;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Response;

/**
 * Created by fritz on 09.07.17.
 */

public class DrinkCache {
    private static DrinkCache instance = null;
    private Context context;
    private LoadingCache<String, Drink> drinks;
    private HashSet<String> list_of_drinks = new HashSet<>();


    public static DrinkCache getInstance(Context context){
        if(instance == null){
            instance = new DrinkCache(context);
        }
        return instance;
    }

    public void updateListDrinks(List<String> drinks_list){
        list_of_drinks.addAll(drinks_list);
        drinks.getAllPresent(drinks_list);
    }

    private static final String TAG = "PersonCache";

    protected DrinkCache(Context context) {
        this.context = context;
        EventBus.getDefault().register(this);
        drinks = CacheBuilder.newBuilder().maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<String, Drink>() {
                            public Drink load(String key) throws NetworkErrorException {
                                try {
                                    Response<Drink> response = DosService.getInstance(DrinkCache.this.context).getDrink(key).execute();
                                    if(response.code() == 200){
                                        EventBus.getDefault().post(new DrinkUpdated(response.body()));
                                        return response.body();
                                    }
                                }catch (IOException e){
                                    Log.e(TAG, e.getMessage());
                                }
                                throw new NetworkErrorException();
                            };
                        });
    }
}
