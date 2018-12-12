package com.avardonigltd.mobilemedicalaid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class refreshRequest {
    @Expose
    @SerializedName("client_id") String CLient_id;

    public refreshRequest(String CLient_id) {
        this.CLient_id = CLient_id;
    }

    public String getCLient_id() {
        return CLient_id;
    }

    public void setCLient_id(String CLient_id) {
        this.CLient_id = CLient_id;
    }
}
