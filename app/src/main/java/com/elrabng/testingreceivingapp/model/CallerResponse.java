package com.elrabng.testingreceivingapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallerResponse {
    @Expose @SerializedName("status") boolean status;
    @Expose @SerializedName("message") String message;
    @Expose @SerializedName("http_code") int code;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        @Expose @SerializedName("case_id") String caseId;
        @Expose @SerializedName("client_name") String clientName;
        @Expose @SerializedName("case_status") String caseStatus;
        @Expose @SerializedName("sub_status") String subStatus;
        @Expose @SerializedName("client_email") String clientEmail;
        @Expose @SerializedName("client_phonenumber") String clientPhoneNumber;
        @Expose @SerializedName("avatar") String avatar;
        @Expose @SerializedName("content") String contentModel2;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getContentModel2() {
            return contentModel2;
        }

        public void setContentModel2(String contentModel) {
            this.contentModel2 = contentModel;
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
