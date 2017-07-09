package com.quappi.scriptkiddi.getraenke.controller;

import android.content.Context;
import android.util.Log;

import com.quappi.scriptkiddi.getraenke.api.DosService;
import com.quappi.scriptkiddi.getraenke.events.PersonControllerInitFinished;
import com.quappi.scriptkiddi.getraenke.events.PersonUpdated;
import com.quappi.scriptkiddi.getraenke.utils.Person;

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

public class personController {

    private static final String TAG = "PersonController";
    private static HashMap<String, Person> personHashMap = new HashMap<>();
    private static ArrayList<String> personList = new ArrayList<>();

    public static void init(final Context context) {
        Log.e(TAG, "test3");
        DosService.getInstance(context).getUsers().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {

                if (response.isSuccessful()) {

                    for (String s : response.body()) {
                        DosService.getInstance(context).getUser(s).enqueue(new Callback<Person>() {
                            @Override
                            public void onResponse(Call<Person> call, Response<Person> response) {
                                if (response.isSuccessful()) {
                                    Person p = response.body();
                                    p.setPermissions(permissionsController.get(p.getPermissionGroup()));
                                    personHashMap.put(response.body().getUsername(), p);
                                    EventBus.getDefault().post(new PersonUpdated(response.body()));
                                } else {
                                    Log.e(TAG, "Errorcode " + Integer.toString(response.code()));
                                }
                                if (personList.size() == personHashMap.size()) {
                                    EventBus.getDefault().post(new PersonControllerInitFinished());
                                }
                            }

                            @Override
                            public void onFailure(Call<Person> call, Throwable t) {
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

    public static void add(Person person) {
        personHashMap.put(person.getUsername(), person);
    }

    public static void put(String username, Person person) {
        if (personHashMap.containsKey(username)) {
            if (!personHashMap.get(username).equals(person)) {
                //TODO push to server
                personHashMap.put(username, person);
                EventBus.getDefault().post(new PersonUpdated(person));
            }
        } else {
            personHashMap.put(username, person);
            EventBus.getDefault().post(new PersonUpdated(person));
        }
    }

    public static void remove(String username) {
        personHashMap.remove(username);
    }

    public static Person get(String username) {
        return personHashMap.get(username);
    }

    public static Collection<Person> getAll() {
        return personHashMap.values();
    }


}
