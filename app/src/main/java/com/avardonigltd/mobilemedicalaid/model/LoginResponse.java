package com.avardonigltd.mobilemedicalaid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @Expose @SerializedName("status")
    boolean status;
    @Expose @SerializedName("message")
    String message;
    @Expose @SerializedName("token")
    String token;
    @Expose @SerializedName("data")
    RegistrationResponse.Datum data;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RegistrationResponse.Datum getData() {
        return data;
    }

    public void setData(RegistrationResponse.Datum data) {
        this.data = data;
    }

    public LoginResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
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
