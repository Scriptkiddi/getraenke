package com.quappi.scriptkiddi.getraenke.events;

import com.quappi.scriptkiddi.getraenke.utils.Drink;

/**
 * Created by fritz on 09.07.17.
 */

public class DrinkUpdated {
    private final Drink drink;

    public DrinkUpdated(Drink body) {
        this.drink = body;
    }

    public Drink getDrink() {
        return drink;
    }
}
