package com.avardonigltd.mobilemedicalaid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefreshDataRequest {
    @Expose
    @SerializedName("client_id") String client_id;

    public RefreshDataRequest(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
