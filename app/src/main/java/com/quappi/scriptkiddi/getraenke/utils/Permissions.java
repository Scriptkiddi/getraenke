package com.quappi.scriptkiddi.getraenke.utils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by michel on 7/8/17.
 */

public class Permissions implements Serializable {
    @SerializedName("Type")
    private String type;
    @SerializedName("PatchDrinkEveryone")
    private boolean patchDrinkEveryone;
    @SerializedName("ModSuppliers")
    private boolean modSupplier;
    @SerializedName("ModDrink")
    private boolean modDrink;
    @SerializedName("ModUser")
    private boolean modUser;
    @SerializedName("SetOwnPassword")
    private boolean setOwnPass;

    public Permissions(boolean patchDrinkEveryone, boolean modSupplier, boolean modDrink, boolean modUser, boolean setOwnPass) {
        this.patchDrinkEveryone = patchDrinkEveryone;
        this.modSupplier = modSupplier;
        this.modDrink = modDrink;
        this.modUser = modUser;
        this.setOwnPass = setOwnPass;
    }

    public boolean canPatchAllDrinks() {
        return patchDrinkEveryone;
    }

    public boolean canModifySupplier() {
        return modSupplier;
    }

    public boolean canModifyDrinks() {
        return modDrink;
    }

    public boolean canModifyUsers() {
        return modUser;
    }

    public boolean canSetOwnPassword() {
        return setOwnPass;
    }

    @Override
    public String toString() {
        return "Permissions{" +
                "patchDrinkEveryone=" + patchDrinkEveryone +
                ", modSupplier=" + modSupplier +
                ", modDrink=" + modDrink +
                ", modUser=" + modUser +
                ", setOwnPass=" + setOwnPass +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPatchDrinkAll() {
        return patchDrinkEveryone;
    }

    public void setPatchDrinkAll(boolean patchDrinkAll) {
        this.patchDrinkEveryone = patchDrinkAll;
    }

    public boolean isModSupplier() {
        return modSupplier;
    }

    public void setModSupplier(boolean modSupplier) {
        this.modSupplier = modSupplier;
    }

    public boolean isModDrink() {
        return modDrink;
    }

    public void setModDrink(boolean modDrink) {
        this.modDrink = modDrink;
    }

    public boolean isModUser() {
        return modUser;
    }

    public void setModUser(boolean modUser) {
        this.modUser = modUser;
    }

    public boolean isSetOwnPass() {
        return setOwnPass;
    }

    public void setSetOwnPass(boolean setOwnPass) {
        this.setOwnPass = setOwnPass;
    }
}
