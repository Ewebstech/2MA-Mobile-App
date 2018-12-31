package com.avardonigltd.mobilemedicalaid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallResponseInit {

    @Expose @SerializedName("Status") boolean status;
    @Expose @SerializedName("message") String message;
    @Expose @SerializedName("data") Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        @Expose @SerializedName("client_id") String clientId;
        @Expose @SerializedName("phonenumber") String phoneNumber;
        @Expose @SerializedName("case_id") String caseId;
        @Expose @SerializedName("client_name") String clientName;
        @Expose @SerializedName("client_email") String clientEmail;
        @Expose @SerializedName("client_phonenumber") String clientPhoneNumber;
        @Expose @SerializedName("client_package") String clientPackage;
        @Expose @SerializedName("case_status") String caseStatus;
        @Expose @SerializedName("sub_status") String subStatus;



        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getCaseId() {
            return caseId;
        }

        public void setCaseId(String caseId) {
            this.caseId = caseId;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getClientEmail() {
            return clientEmail;
        }

        public void setClientEmail(String clientEmail) {
            this.clientEmail = clientEmail;
        }

        public String getClientPhoneNumber() {
            return clientPhoneNumber;
        }

        public void setClientPhoneNumber(String clientPhoneNumber) {
            this.clientPhoneNumber = clientPhoneNumber;
        }

        public String getClientPackage() {
            return clientPackage;
        }

        public void setClientPackage(String clientPackage) {
            this.clientPackage = clientPackage;
        }

        public String getCaseStatus() {
            return caseStatus;
        }

        public void setCaseStatus(String caseStatus) {
            this.caseStatus = caseStatus;
        }

        public String getSubStatus() {
            return subStatus;
        }

        public void setSubStatus(String subStatus) {
            this.subStatus = subStatus;
        }
    }
}
