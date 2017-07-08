package com.quappi.scriptkiddi.getraenke.controller;

import com.quappi.scriptkiddi.getraenke.events.PersonUpdated;
import com.quappi.scriptkiddi.getraenke.utils.Person;

import org.greenrobot.eventbus.EventBus;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by fritz on 09.07.17.
 */

public class personController {

    private static HashMap<String, Person> personHashMap = new HashMap<>();

    public static void add(Person person){
        personHashMap.put(person.getUsername(), person);
    }

    public static void put(String username, Person person){
        if(personHashMap.containsKey(username)){
            if(!personHashMap.get(username).equals(person)){
                //TODO push to server
                personHashMap.put(username, person);
                EventBus.getDefault().post(new PersonUpdated(person));
            }
        }else{
            personHashMap.put(username, person);
            EventBus.getDefault().post(new PersonUpdated(person));
        }
    }

    public static void remove(String username){
        personHashMap.remove(username);
    }

    public static Person get(String username){
        return personHashMap.get(username);
    }

    public static Collection<Person> getAll(){
        return personHashMap.values();
    }


}
