package com.elrabng.testingreceivingapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentModel {
    @Expose
    @SerializedName("firstname") String firstname;
    @Expose @SerializedName("lastname") String lastname;
    @Expose @SerializedName("role") String role;
    @Expose @SerializedName("phonenumber") String phonenumber;
    @Expose @SerializedName("email") String email;
    @Expose @SerializedName("gender") String gender;
    @Expose @SerializedName("avatar") String avatar;
    @Expose @SerializedName("package") String Packages;
    @Expose @SerializedName("contact_address") String contact_address;
    @Expose @SerializedName("client_id") String client_id;
    @Expose @SerializedName("calls") String calls;
    @Expose @SerializedName("status") String status;
    @Expose @SerializedName("amount") String amount;
    @Expose @SerializedName("Kyc") KYC kyc;


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    public KYC getKyc() {
        return kyc;
    }

    public void setKyc(KYC kyc) {
        this.kyc = kyc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPackages() {
        return Packages;
    }

    public void setPackages(String packages) {
        Packages = packages;
    }

    public String getContact_address() {
        return contact_address;
    }

    public void setContact_address(String contact_address) {
        this.contact_address = contact_address;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getCalls() {
        return calls;
    }

    public void setCalls(String calls) {
        this.calls = calls;
    }


    public class KYC {
        @Expose @SerializedName("city") String city;
        @Expose @SerializedName("contact_address") String contact_address;
        @Expose @SerializedName("country") String country;
        @Expose @SerializedName("dob") String dob;
        @Expose @SerializedName("email") String email;
        @Expose @SerializedName("emergency_contact_name_1") String eme_contact_name_1;
        @Expose @SerializedName("emergency_contact_name_2") String eme_contact_name_2;
        @Expose @SerializedName("emergency_contact_num_1") String eme_contact_num_1;
        @Expose @SerializedName("emergency_contact_num_2") String eme_contact_num_2;
        @Expose @SerializedName("hmo_reg_status") String hmo_status;
        @Expose @SerializedName("hmo_information") String hmo_info;
        @Expose @SerializedName("medical_condition") String medical_condition;
        @Expose @SerializedName("medical_condition_details") String medical_condition_details;
        @Expose @SerializedName("phonenumber") String phonenumber;
        @Expose @SerializedName("postal_code") String postal_code;
        @Expose @SerializedName("treatment_status") String treatment_status;



        public KYC(String city, String contact_address, String country, String dob, String email,
                   String eme_contact_name_1, String eme_contact_name_2, String eme_contact_num_1,
                   String eme_contact_num_2, String hmo_status, String medical_condition,
                   String medical_condition_details, String phonenumber, String postal_code, String treatment_status) {
            this.city = city;
            this.contact_address = contact_address;
            this.country = country;
            this.dob = dob;
            this.email = email;
            this.eme_contact_name_1 = eme_contact_name_1;
            this.eme_contact_name_2 = eme_contact_name_2;
            this.eme_contact_num_1 = eme_contact_num_1;
            this.eme_contact_num_2 = eme_contact_num_2;
            this.hmo_status = hmo_status;
            this.medical_condition = medical_condition;
            this.medical_condition_details = medical_condition_details;
            this.phonenumber = phonenumber;
            this.postal_code = postal_code;
            this.treatment_status = treatment_status;
        }

        public String getHmo_info() {
            return hmo_info;
        }

        public void setHmo_info(String hmo_info) {
            this.hmo_info = hmo_info;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getContact_address() {
            return contact_address;
        }

        public void setContact_address(String contact_address) {
            this.contact_address = contact_address;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEme_contact_name_1() {
            return eme_contact_name_1;
        }

        public void setEme_contact_name_1(String eme_contact_name_1) {
            this.eme_contact_name_1 = eme_contact_name_1;
        }

        public String getEme_contact_name_2() {
            return eme_contact_name_2;
        }

        public void setEme_contact_name_2(String eme_contact_name_2) {
            this.eme_contact_name_2 = eme_contact_name_2;
        }

        public String getEme_contact_num_1() {
            return eme_contact_num_1;
        }

        public void setEme_contact_num_1(String eme_contact_num_1) {
            this.eme_contact_num_1 = eme_contact_num_1;
        }

        public String getEme_contact_num_2() {
            return eme_contact_num_2;
        }

        public void setEme_contact_num_2(String eme_contact_num_2) {
            this.eme_contact_num_2 = eme_contact_num_2;
        }

        public String getHmo_status() {
            return hmo_status;
        }

        public void setHmo_status(String hmo_status) {
            this.hmo_status = hmo_status;
        }

        public String getMedical_condition() {
            return medical_condition;
        }

        public void setMedical_condition(String medical_condition) {
            this.medical_condition = medical_condition;
        }

        public String getMedical_condition_details() {
            return medical_condition_details;
        }

        public void setMedical_condition_details(String medical_condition_details) {
            this.medical_condition_details = medical_condition_details;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public String getPostal_code() {
            return postal_code;
        }

        public void setPostal_code(String postal_code) {
            this.postal_code = postal_code;
        }

        public String getTreatment_status() {
            return treatment_status;
        }

        public void setTreatment_status(String treatment_status) {
            this.treatment_status = treatment_status;
        }
    }
}
