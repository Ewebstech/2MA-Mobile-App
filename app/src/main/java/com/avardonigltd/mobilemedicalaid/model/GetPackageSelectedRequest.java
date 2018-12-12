package com.avardonigltd.mobilemedicalaid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPackageSelectedRequest {
    @Expose @SerializedName("package") String PackageType;
    @Expose @SerializedName("client_id") String client_id;

    public GetPackageSelectedRequest(String packageType, String client_id) {
        PackageType = packageType;
        this.client_id = client_id;
    }

    public String getPackageType() {
        return PackageType;
    }

    public void setPackageType(String packageType) {
        PackageType = packageType;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }


}
