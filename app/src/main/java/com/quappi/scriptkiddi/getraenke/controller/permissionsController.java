package com.quappi.scriptkiddi.getraenke.controller;

import com.quappi.scriptkiddi.getraenke.utils.Permissions;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by fritz on 09.07.17.
 */

public class permissionsController {

    private static HashMap<String, Permissions> permissionsHashMap = new HashMap<>();

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
