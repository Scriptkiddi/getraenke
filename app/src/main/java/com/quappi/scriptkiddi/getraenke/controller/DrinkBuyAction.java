package com.quappi.scriptkiddi.getraenke.controller;

import com.quappi.scriptkiddi.getraenke.utils.Drink;
import com.quappi.scriptkiddi.getraenke.utils.Person;

/**
 * Created by michel on 7/9/17.
 */

public class DrinkBuyAction {
    private Person person;
    private Drink drink;
    private boolean valid = true;

    public DrinkBuyAction(Person person, Drink drink) {
        this.person = person;
        this.drink = drink;
    }

    public Person getPerson() {
        return person;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public String toString() {
        return "DrinkBuyAction{" +
                "person=" + person +
                ", drink=" + drink +
                ", valid=" + valid +
                '}';
    }

    public void putOrder() {
        if (valid) {
            //TODO send order
            //TODO update credit? and display in drinks view
        }
    }
}
