package com.elrabng.testingreceivingapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompletedCallDetailsRequest {
    @Expose @SerializedName("doctor_id") String doctorId;
    @Expose @SerializedName("client_phonenumber") String clientPhoneNumber;
    @Expose @SerializedName("case_id") String caseId;

    public CompletedCallDetailsRequest(String doctorId, String clientPhoneNumber, String caseId) {
        this.doctorId = doctorId;
        this.clientPhoneNumber = clientPhoneNumber;
        this.caseId = caseId;
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

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
}
