package com.quappi.scriptkiddi.getraenke.utils;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fritz on 08.07.17.
 */

public class Drink {
    @SerializedName("Ean")
    private String ean;
    @SerializedName("Name")
    private String name;
    @SerializedName("PriceResell")
    private double resellPrice;
    @SerializedName("Amount")
    private int amountInStock;
    private double volume;


    //private Drawable image;
    @SerializedName("Supplier")
    private Supplier supplier;


    private Integer supplierID;
    @SerializedName("ImgUrl")
    private String imgURL;

    public Drink(String ean, String name, double resellPrice, double volume, String imgURL) {
        this.ean = ean;
        this.name = name;
        this.resellPrice = resellPrice;
        this.volume = volume;
        this.imgURL = imgURL;
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
        return null;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
