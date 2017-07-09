package com.quappi.scriptkiddi.getraenke.controller;

import android.content.Context;
import android.util.Log;

import com.quappi.scriptkiddi.getraenke.api.DosService;
import com.quappi.scriptkiddi.getraenke.utils.Permissions;

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

public class permissionsController {

    private static List<String> permissionsList = new ArrayList<>();
    private static final String TAG = "PermissionController";
    private static HashMap<String, Permissions> permissionsHashMap = new HashMap<>();

    public static void init(final Context context){
        DosService.getInstance(context).getPermissions().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()){
                    for(String s: response.body()){
                        permissionsList.add(s);
                        DosService.getInstance(context).getPermission(s).enqueue(new Callback<Permissions>() {
                            @Override
                            public void onResponse(Call<Permissions> call, Response<Permissions> response) {
                                if (response.isSuccessful()) {
                                    permissionsHashMap.put(response.body().getType(), response.body());
                                }
                                if (permissionsHashMap.size() ==  permissionsList.size()){
                                    personController.init(context);
                                }
                            }

                            @Override
                            public void onFailure(Call<Permissions> call, Throwable t) {
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

    public static void add(Permissions permissions){
        permissionsHashMap.put(permissions.getType(), permissions);
    }

    public static void put(String username, Permissions permissions){

    }

    public static void remove(String username){
        permissionsHashMap.remove(username);
    }

    public static Permissions get(String type){
        return permissionsHashMap.get(type);
    }

    public static Collection<Permissions> getAll(){
        return permissionsHashMap.values();
    }

}
