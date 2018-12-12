package com.avardonigltd.mobilemedicalaid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KYCResponse {
    @Expose @SerializedName("status") boolean status;
    @Expose @SerializedName("message") String message;
    @Expose @SerializedName("data") Datud data;

//    @Expose @SerializedName("data") KYCRequest data;

    public KYCResponse(boolean status, String message, Datud data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

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

    public Datud getData() {
        return data;
    }

    public void setData(Datud data) {
        this.data = data;
    }

    public class Datud{
        @Expose @SerializedName("Kyc") KYCRequest kycRequest;
        public Datud(KYCRequest kycRequest) {
            this.kycRequest = kycRequest;
        }

        public KYCRequest getKycRequest() {
            return kycRequest;
        }

        public void setKycRequest(KYCRequest kycRequest) {
            this.kycRequest = kycRequest;
        }
    }
}
