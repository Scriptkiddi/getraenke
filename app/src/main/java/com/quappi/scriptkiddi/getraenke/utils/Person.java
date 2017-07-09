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
    private String permissionGroup;
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
        System.out.println("change last name to "+lastName);
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


    public String getPermissionGroup() {
        return permissionGroup;
    }

    public void setPermissionGroup(String permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null)
            return false;
        return lastName != null ? lastName.equals(person.lastName) : person.lastName == null;

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;

    }
}
