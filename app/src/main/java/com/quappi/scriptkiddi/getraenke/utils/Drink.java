package com.quappi.scriptkiddi.getraenke.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by fritz on 08.07.17.
 */

public class Drink {
    private String name;
    private double price;
    private double volume;
    private Drawable image;

    public Drink(String name, double price, double volume, Drawable image) {
        this.name = name;
        this.price = price;
        this.volume = volume;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public Drawable getImage() {
        return image;
    }
}
