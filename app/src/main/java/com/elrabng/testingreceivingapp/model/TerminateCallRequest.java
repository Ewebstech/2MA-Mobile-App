package com.elrabng.testingreceivingapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TerminateCallRequest {
    @Expose @SerializedName("phonenumber") String phonenumber;

    public TerminateCallRequest(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

}
