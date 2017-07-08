package com.quappi.scriptkiddi.getraenke.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by fritz on 08.07.17.
 */

public class Drink {
    private String name;
    private double resellPrice;
    private double volume;
    private Drawable image;

    public Drink(String name, double resellPrice, double volume, Drawable image) {
        this.name = name;
        this.resellPrice = resellPrice;
        this.volume = volume;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getResellPrice() {
        return resellPrice;
    }

    public void setResellPrice(double resellPrice) {
        this.resellPrice = resellPrice;
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
