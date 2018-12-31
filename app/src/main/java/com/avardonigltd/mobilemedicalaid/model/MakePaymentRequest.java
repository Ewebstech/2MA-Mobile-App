package com.avardonigltd.mobilemedicalaid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MakePaymentRequest {
   @Expose @SerializedName("package") String packageStr;
    @Expose @SerializedName("client_id")  String clientId;
    @Expose @SerializedName("amount") String amount;
    @Expose @SerializedName("email") String email;
    @Expose @SerializedName("metadata") MetaData metaData;

//    public MakePaymentRequest(String packageStr, String clientId, String amount, String email) {
//        this.packageStr = packageStr;
//        this.clientId = clientId;
//        this.amount = amount;
//        this.email = email;
//    }


    public MakePaymentRequest(String packageStr, String clientId, String amount, String email, MetaData metaData) {
        this.packageStr = packageStr;
        this.clientId = clientId;
        this.amount = amount;
        this.email = email;
        this.metaData = metaData;
    }

    public String getPacakageStr() {
        return packageStr;
    }

    public void setPacakageStr(String pacakageStr) {
        this.packageStr = pacakageStr;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static class MetaData{
        @Expose @SerializedName("custom_fields") Custom custom;

        public MetaData(Custom custom) {
            this.custom = custom;
        }

        public static class Custom {
            public List<DataSent> dataSentList;

            public Custom(List<DataSent> dataSentList) {
                this.dataSentList = dataSentList;
            }

            public static class  DataSent{
               @Expose @SerializedName("package") String packageStr;
                @Expose @SerializedName("client_id") String clientId;

                public DataSent(String packageStr, String clientId) {
                    this.packageStr = packageStr;
                    this.clientId = clientId;
                }

                public String getPackageStr() {
                    return packageStr;
                }

                public void setPackageStr(String packageStr) {
                    this.packageStr = packageStr;
                }

                public String getClientId() {
                    return clientId;
                }

                public void setClientId(String clientId) {
                    this.clientId = clientId;
                }
            }
        }
    }
}
