package com.quappi.scriptkiddi.getraenke.utils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fritz on 08.07.17.
 */

public class Person implements Serializable {
    @SerializedName("FirstName")
    private String firstName;
    @SerializedName("LastName")
    private String lastName;
    @SerializedName("Username")
    private String username;
    @SerializedName("Credit")
    private Double credit;
    @SerializedName("Permissions")
    private Permissions permissions;

    public Person(String firstName, String lastName, Permissions permissions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.permissions = permissions;
    }

    public Person(String firstName, String lastName, Permissions permissions, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.permissions = permissions;
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }
}
