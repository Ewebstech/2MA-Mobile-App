package com.avardonigltd.mobilemedicalaid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallRequest {
    @Expose @SerializedName("client_id") String clientId;
    @Expose @SerializedName("phonenumber") String phoneNumber;
    @Expose @SerializedName("medicalNeedStr") String medicalNeedStr;
    @Expose @SerializedName("severeStr") String severeStr;
    @Expose @SerializedName("allergyFamily") String allergyFamily;
    @Expose @SerializedName("takenTreatmentStr") String takenTreatmentStr;
    @Expose @SerializedName("allergiesStr") String allergiesStr;
    @Expose @SerializedName("otherInfoStr") String otherInfoStr;


    public CallRequest(String clientId, String phoneNumber) {
        this.clientId = clientId;
        this.phoneNumber = phoneNumber;
    }

    public CallRequest(String clientId, String phoneNumber, String medicalNeedStr, String severeStr,
                       String allergyFamily, String takenTreatmentStr, String allergiesStr, String otherInfoStr) {
        this.clientId = clientId;
        this.phoneNumber = phoneNumber;
        this.medicalNeedStr = medicalNeedStr;
        this.severeStr = severeStr;
        this.allergyFamily = allergyFamily;
        this.takenTreatmentStr = takenTreatmentStr;
        this.allergiesStr = allergiesStr;
        this.otherInfoStr = otherInfoStr;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber;}
}
