package com.avardonigltd.mobilemedicalaid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MakePaymentResponse {
    @Expose @SerializedName("status") boolean status;
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
        @Expose @SerializedName("url") String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
