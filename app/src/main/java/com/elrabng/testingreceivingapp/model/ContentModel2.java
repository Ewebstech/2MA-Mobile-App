package com.elrabng.testingreceivingapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentModel2 {
    @Expose @SerializedName("medicalNeedStr") String medicalNeedSP;
    @Expose @SerializedName("severeStr") String severeSP;
    @Expose @SerializedName("allergyFamily") String allergySP;
    @Expose @SerializedName("takenTreatmentStr") String takenTreatment;
    @Expose @SerializedName("allergiesStr") String allergies;
    @Expose @SerializedName("otherInfoStr") String otherInfo;

    public String getMedicalNeedSP() {
        return medicalNeedSP;
    }

    public void setMedicalNeedSP(String medicalNeedSP) {
        this.medicalNeedSP = medicalNeedSP;
    }

    public String getSevereSP() {
        return severeSP;
    }

    public void setSevereSP(String severeSP) {
        this.severeSP = severeSP;
    }

    public String getAllergySP() {
        return allergySP;
    }

    public void setAllergySP(String allergySP) {
        this.allergySP = allergySP;
    }

    public String getTakenTreatment() {
        return takenTreatment;
    }

    public void setTakenTreatment(String takenTreatment) {
        this.takenTreatment = takenTreatment;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }
}
