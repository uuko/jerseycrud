package com.jersey.test.dao;

//import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.SerializedName;

import javax.persistence.Column;

public class StudentRequest {
    @SerializedName(value = "firstName")
    private String firstName;

    @SerializedName(value = "lastName")
    private String lastName;

    @SerializedName(value = "email")
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
