package com.avardonigltd.mobilemedicalaid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class kycDataModel {
    @Expose @SerializedName("Kyc") KYC kyc;

    public KYC getKyc() {
        return kyc;
    }

    public void setKyc(KYC kyc) {
        this.kyc = kyc;
    }

    public class KYC {
        @Expose @SerializedName("treatment_status")String treatment_status;
        @Expose @SerializedName("email")String email;
        @Expose @SerializedName("phonenumber")String phonenumber;
        @Expose @SerializedName("emergency_contact_name_1")String emergencyContact1;
        @Expose @SerializedName("emergency_contact_name_2")String emergencyContact2;
        @Expose @SerializedName("emergency_contact_num_1")String emergencyContactNum1;
        @Expose @SerializedName("emergency_contact_num_2")String emergencyContactNum2;
        @Expose @SerializedName("hmo_reg_status")String hmoRegStatus ;
        @Expose @SerializedName("hmo_information")String hmoInformation;
        @Expose @SerializedName("medical_condition_details")String medicalConditionDetails;
        @Expose @SerializedName("contact_address")String contactAddress;
        @Expose @SerializedName("city")String city;
        @Expose @SerializedName("postal_code")String postaCode;
        @Expose @SerializedName("country")String country;
        @Expose @SerializedName("role")String role;
        @Expose @SerializedName("medical_conition")String medicalCondition;
        @Expose @SerializedName("dob")String dob;

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

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public String getEmergencyContact1() {
            return emergencyContact1;
        }

        public void setEmergencyContact1(String emergencyContact1) {
            this.emergencyContact1 = emergencyContact1;
        }

        public String getEmergencyContact2() {
            return emergencyContact2;
        }

        public void setEmergencyContact2(String emergencyContact2) {
            this.emergencyContact2 = emergencyContact2;
        }

        public String getEmergencyContactNum1() {
            return emergencyContactNum1;
        }

        public void setEmergencyContactNum1(String emergencyContactNum1) {
            this.emergencyContactNum1 = emergencyContactNum1;
        }

        public String getEmergencyContactNum2() {
            return emergencyContactNum2;
        }

        public void setEmergencyContactNum2(String emergencyContactNum2) {
            this.emergencyContactNum2 = emergencyContactNum2;
        }

        public String getHmoRegStatus() {
            return hmoRegStatus;
        }

        public void setHmoRegStatus(String hmoRegStatus) {
            this.hmoRegStatus = hmoRegStatus;
        }

        public String getHmoInformation() {
            return hmoInformation;
        }

        public void setHmoInformation(String hmoInformation) {
            this.hmoInformation = hmoInformation;
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

        public String getPostaCode() {
            return postaCode;
        }

        public void setPostaCode(String postaCode) {
            this.postaCode = postaCode;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
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
    }
}
