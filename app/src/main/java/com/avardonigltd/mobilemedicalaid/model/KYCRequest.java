package com.avardonigltd.mobilemedicalaid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KYCRequest {
    @Expose @SerializedName("treatment_status") String treatment_status;
    @Expose @SerializedName("email") String email;
    @Expose @SerializedName("phonenumber") String phoneNumber;
    @Expose @SerializedName("emergency_contact_name_1") String emer_contact_name1;
    @Expose @SerializedName("emergency_contact_name_2") String emer_contact_name2;
    @Expose @SerializedName("emergency_contact_num_1") String emer_contact_num1;
    @Expose @SerializedName("emergency_contact_num_2") String emer_contact_num2;
    @Expose @SerializedName("hmo_reg_status") String hmoRegStatus;
    @Expose @SerializedName("hmo_information") String hmoInfo;
    @Expose @SerializedName("medical_condition_details") String medicalConditionDetails;
    @Expose @SerializedName("contact_address") String contactAddress;
    @Expose @SerializedName("city") String city;
    @Expose @SerializedName("postal_code") String postalCode;
    @Expose @SerializedName("country") String country;
    @Expose @SerializedName("medical_condition") String medicalCondition;
    @Expose @SerializedName("dob") String dob;
    @Expose @SerializedName("role") String role;

    public KYCRequest(String treatment_status, String email, String phoneNumber,
                      String emer_contact_name1, String emer_contact_name2, String emer_contact_num1,
                      String emer_contact_num2, String hmoRegStatus, String hmoInfo, String medicalConditionDetails,
                      String contactAddress, String city, String postalCode, String country, String medicalCondition,
                      String dob, String role) {
        this.treatment_status = treatment_status;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.emer_contact_name1 = emer_contact_name1;
        this.emer_contact_name2 = emer_contact_name2;
        this.emer_contact_num1 = emer_contact_num1;
        this.emer_contact_num2 = emer_contact_num2;
        this.hmoRegStatus = hmoRegStatus;
        this.hmoInfo = hmoInfo;
        this.medicalConditionDetails = medicalConditionDetails;
        this.contactAddress = contactAddress;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.medicalCondition = medicalCondition;
        this.dob = dob;
        this.role = role;
    }

    public String getTreatment_status() {
        return treatment_status;
    }

    public void setTreatment_status(String treatment_status) {
        this.treatment_status = treatment_status;
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

    public String getEmer_contact_name1() {
        return emer_contact_name1;
    }

    public void setEmer_contact_name1(String emer_contact_name1) {
        this.emer_contact_name1 = emer_contact_name1;
    }

    public String getEmer_contact_name2() {
        return emer_contact_name2;
    }

    public void setEmer_contact_name2(String emer_contact_name2) {
        this.emer_contact_name2 = emer_contact_name2;
    }

    public String getEmer_contact_num1() {
        return emer_contact_num1;
    }

    public void setEmer_contact_num1(String emer_contact_num1) {
        this.emer_contact_num1 = emer_contact_num1;
    }

    public String getEmer_contact_num2() {
        return emer_contact_num2;
    }

    public void setEmer_contact_num2(String emer_contact_num2) {
        this.emer_contact_num2 = emer_contact_num2;
    }

    public String getHmoRegStatus() {
        return hmoRegStatus;
    }

    public void setHmoRegStatus(String hmoRegStatus) {
        this.hmoRegStatus = hmoRegStatus;
    }

    public String getHmoInfo() {
        return hmoInfo;
    }

    public void setHmoInfo(String hmoInfo) {
        this.hmoInfo = hmoInfo;
    }

    public String getMedicalConditionDetails() {
        return medicalConditionDetails;
    }

    public void setMedicalConditionDetails(String medicalConditionDetails) {
        this.medicalConditionDetails = medicalConditionDetails;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
