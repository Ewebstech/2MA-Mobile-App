package com.elrabng.testingreceivingapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallerRequest {
    @Expose @SerializedName("doctor_id") String doctorId;
    @Expose @SerializedName("client_phonenumber") String clientPhoneNumber;

    public CallerRequest(String doctorId, String clientPhoneNumber) {
        this.doctorId = doctorId;
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }
}
