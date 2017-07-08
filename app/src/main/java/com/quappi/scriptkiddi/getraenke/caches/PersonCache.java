package com.quappi.scriptkiddi.getraenke.caches;

import android.util.Log;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.quappi.scriptkiddi.getraenke.api.DosService;
import com.quappi.scriptkiddi.getraenke.controller.personController;
import com.quappi.scriptkiddi.getraenke.events.PersonUpdated;
import com.quappi.scriptkiddi.getraenke.events.UserListUpdated;
import com.quappi.scriptkiddi.getraenke.utils.Person;
import com.quappi.scriptkiddi.getraenke.utils.exception.NetworkErrorException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fritz on 08.07.17.
 */

public class PersonCache {
    private static PersonCache instance = null;
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://35.156.87.172:9080/")
            .build();
    private DosService service = retrofit.create(DosService.class);
    private LoadingCache<String, Person> people;
    private HashSet<String> list_of_users = new HashSet<>();
    private boolean pushToListView = false;

    public static PersonCache getInstance(){
        if(instance == null){
            instance = new PersonCache();
        }
        return instance;
    }

    private static final String TAG = "PersonCache";

    protected PersonCache() {
        EventBus.getDefault().register(this);
        people = CacheBuilder.newBuilder().maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<String, Person>() {
                            public Person load(String key) throws NetworkErrorException{
                                try {
                                    Response<Person> response = service.getUser(key).execute();
                                    if(response.code() == 200){
                                        EventBus.getDefault().post(new PersonUpdated(response.body()));
                                        return response.body();
                                    }
                                }catch (IOException e){
                                    Log.e(TAG, e.getMessage());
                                }
                                throw new NetworkErrorException();
                            };
                        });
        refreshUserList();
    }

    @Subscribe
    public void updateAllUsers(UserListUpdated event) {
        for(String username: list_of_users){
                updateUser(username);
        }
    }

    public void updateUser(String username){
        service.getUser(username).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if(response.code() == 200){
                    Log.e(TAG, Boolean.toString(response.body() == null ));
                    people.put(response.body().getUsername(), response.body());
                    personController.put(response.body().getUsername(), response.body());
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public Collection<Person> getAll(){
        return people.asMap().values();
    }

    public void refreshUserList(){
        Call<List<String>> people_call = service.listUsers();
        people_call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                int statusCode = response.code();
                if(statusCode==200){
                    List<String> user = response.body();
                    list_of_users.addAll(user);
                    EventBus.getDefault().post(new UserListUpdated());
                }

            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }
}
