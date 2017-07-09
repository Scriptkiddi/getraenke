package com.quappi.scriptkiddi.getraenke.utils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fritz on 09.07.17.
 */

public class Supplier {

    @SerializedName("ID")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Address")
    private String address;
    @SerializedName("DeliverTime")
    private int deliverTime;

    public Supplier(int id, String name, String address, int deliverTime) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.deliverTime = deliverTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(int deliverTime) {
        this.deliverTime = deliverTime;
    }
}
