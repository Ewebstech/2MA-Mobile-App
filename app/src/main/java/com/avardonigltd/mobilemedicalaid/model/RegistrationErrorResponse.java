package com.avardonigltd.mobilemedicalaid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegistrationErrorResponse {
    @Expose @SerializedName("status")
    String status;
    @Expose @SerializedName("message")
    List<String> message;
    @Expose @SerializedName("data")
    Datum data;
    @Expose @SerializedName("client_id")
    String clientId;

    public RegistrationErrorResponse(String status, List<String> message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public String getClientId() {
        return clientId;
    }

    public Datum getData() {
        return data;
    }

    public void setData(Datum data) {
        this.data = data;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public class Datum {
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
        @Expose @SerializedName("id")
        String id;


        public Datum(String firstname, String lastname, String email, String phoneNumber,
                     String password) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.password = password;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
