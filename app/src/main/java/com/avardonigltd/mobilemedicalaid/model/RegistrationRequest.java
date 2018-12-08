package com.avardonigltd.mobilemedicalaid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationRequest {
    @Expose @SerializedName("firstname")
    String firstName;
    @Expose @SerializedName("lastname")
    String lastName;
    @Expose @SerializedName("email")
    String email;
    @Expose @SerializedName("phonenumber")
    String phoneNumber;
    @Expose @SerializedName("password")
    String password;
    @Expose @SerializedName("gender")
    String gender;
    @Expose @SerializedName("role")
    String role;

    public RegistrationRequest(String firstName, String lastName, String email, String phoneNumber,
                               String password, String gender,String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.gender = gender;
        this.role = role;
    }

    public String getRole() { return role;}

    public void setRole(String role) { this.role = role;}

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {return gender;}

    public void setGender(String gender) {
        this.gender = gender;
    }
}
