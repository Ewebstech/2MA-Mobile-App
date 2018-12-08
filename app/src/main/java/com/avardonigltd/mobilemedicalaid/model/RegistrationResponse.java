package com.avardonigltd.mobilemedicalaid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegistrationResponse {
    @Expose @SerializedName("status")
    String status;
    @Expose @SerializedName("message")
    String message;
    @Expose @SerializedName("data")
    Datum data;

    public RegistrationResponse(String status, String message, Datum data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Datum getData() {
        return data;
    }

    public void setData(Datum data) {
        this.data = data;
    }



    public static class Datum {
        @Expose @SerializedName("firstname")
        String firstname;
        @Expose @SerializedName("lastname")
        String lastname;
        @Expose @SerializedName("email")
        String email;
        @Expose @SerializedName("phonenumber")
        String phoneNumber;
        @Expose @SerializedName("password")
        String password;
        @Expose @SerializedName("role")
        String role;
        @Expose @SerializedName("client_id")
        String clientId;


        public Datum(String firstname, String lastname, String email, String phoneNumber,
                     String password,String role,String clientId) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.password = password;
            this.role = role;
            this.clientId = clientId;
        }

        public Datum() {
        }


        public void setRole(String role) {
            this.role= role;
        }

        public String getRole() {
            return role;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientId() {
            return clientId;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
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

    }
}
