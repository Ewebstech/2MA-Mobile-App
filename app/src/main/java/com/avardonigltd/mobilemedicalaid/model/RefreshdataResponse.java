package com.avardonigltd.mobilemedicalaid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefreshdataResponse {
    @Expose
    @SerializedName("status")
    boolean status;
    @Expose @SerializedName("message")
    String message;
    @Expose @SerializedName("data")
    LoginResponse.Datum data;

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
        @Expose @SerializedName("role")
        String role;
        @Expose @SerializedName("client_id")
        String clientId;
        @Expose @SerializedName("content")
        String content;

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

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public LoginResponse.Datum getData() {
        return data;
    }

    public void setData(LoginResponse.Datum data) {
        this.data = data;
    }

    public RefreshdataResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public RefreshdataResponse() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
