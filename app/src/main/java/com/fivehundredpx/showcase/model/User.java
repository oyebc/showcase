package com.fivehundredpx.showcase.model;

import com.google.gson.annotations.SerializedName;

public class User {

    private String id;
    @SerializedName("username")
    private String userName;
    @SerializedName("firstname")
    private String firstName;
    @SerializedName("lastname")
    private String lastName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
