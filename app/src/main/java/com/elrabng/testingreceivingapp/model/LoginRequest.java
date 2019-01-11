package com.elrabng.testingreceivingapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @Expose
    @SerializedName("loginparam")
    String loginparam;
    @Expose @SerializedName("password")
    String password;

    public LoginRequest(String loginparam, String password) {
        this.loginparam = loginparam;
        this.password = password;
    }

    public String getLoginparam() {
        return loginparam;
    }

    public void setLoginparam(String email) {
        this.loginparam =loginparam;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
