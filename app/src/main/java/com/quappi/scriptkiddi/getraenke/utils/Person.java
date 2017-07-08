package com.quappi.scriptkiddi.getraenke.utils;

import java.io.Serializable;

/**
 * Created by fritz on 08.07.17.
 */

public class Person implements Serializable {
    private String firstName;
    private String lastName;
    private Permissions permissions;

    public Person(String firstName, String lastName, Permissions permissions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.permissions = permissions;
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

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
