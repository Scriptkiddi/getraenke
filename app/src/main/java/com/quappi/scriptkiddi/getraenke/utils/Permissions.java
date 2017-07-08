package com.quappi.scriptkiddi.getraenke.utils;

import java.io.Serializable;

/**
 * Created by michel on 7/8/17.
 */

public class Permissions implements Serializable {
    private boolean patchDrinkAll;
    private boolean modSupplier;
    private boolean modDrink;
    private boolean modUser;
    private boolean setOwnPass;

    public Permissions(boolean patchDrinkAll, boolean modSupplier, boolean modDrink, boolean modUser, boolean setOwnPass) {
        this.patchDrinkAll = patchDrinkAll;
        this.modSupplier = modSupplier;
        this.modDrink = modDrink;
        this.modUser = modUser;
        this.setOwnPass = setOwnPass;
    }

    public boolean canPatchAllDrinks() {
        return patchDrinkAll;
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
                "patchDrinkAll=" + patchDrinkAll +
                ", modSupplier=" + modSupplier +
                ", modDrink=" + modDrink +
                ", modUser=" + modUser +
                ", setOwnPass=" + setOwnPass +
                '}';
    }
}
